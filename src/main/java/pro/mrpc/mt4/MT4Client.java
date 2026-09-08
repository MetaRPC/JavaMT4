package pro.mrpc.mt4;

import pro.mrpc.mt4.crypt.MT4Encryption;
import pro.mrpc.mt4.crypt.MT4Crypt;
import pro.mrpc.mt4.exception.DecoderException;
import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.MetaTraderRequest;
import pro.mrpc.mt4.network.OutputFactory;
import pro.mrpc.mt4.network.enums.AccountType;
import pro.mrpc.mt4.network.enums.Op;
import pro.mrpc.mt4.network.enums.UpdateAction;
import pro.mrpc.mt4.network.messages.req.OrderTransactionReq;
import pro.mrpc.mt4.network.messages.res.DisconnectEvent;
import pro.mrpc.mt4.network.messages.res.OrderHistoryRes;
import pro.mrpc.mt4.network.messages.res.OrderUpdateEvent;
import pro.mrpc.mt4.network.parts.AccountInfo;
import pro.mrpc.mt4.network.parts.ServerInfo;
import pro.mrpc.mt4.nio.ServerSocket;
import pro.mrpc.mt4.utils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main entry point for the MT4 SDK.
 * Mirrors the MT5Client design: extends ServerSocket, implements InternalMessageHandler.
 *
 * <p>Usage:
 * <pre>{@code
 * MT4Client client = new MT4Client("host", 443, new DefaultMessageHandler());
 * client.connect(12345, "password");
 * }</pre>
 */
public class MT4Client extends ServerSocket implements InternalMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(MT4Client.class);

    private final LoginHelper loginHelper;
    private final MessageDecoder messageDecoder;
    private final MessageHandler messageHandler;
    private final SymbolTable symbolTable = new SymbolTable();
    private volatile AccountInfo accountInfo;
    private volatile ServerInfo serverInfo;
    private final ConcurrentHashMap<Integer, OrderUpdateEvent> openOrders = new ConcurrentHashMap<>();

    private CountDownLatch loginLatch = new CountDownLatch(1);
    private ExecutorService threadsExecutors;

    private volatile boolean reading;
    private long readThreadId = 0;
    private boolean baseAccountRead = false;

    // Stored credentials for side connections (order history, etc.)
    private int storedUsername;
    private String storedPassword;

    private final AtomicInteger requestIdCounter = new AtomicInteger(1);

    // Sync message response infrastructure (mirrors MT5Client)
    private final ConcurrentHashMap<Class<? extends MetaTraderMessage>, ConcurrentLinkedQueue<SynchronousQueue<MetaTraderMessage>>> responseMap = new ConcurrentHashMap<>();
    private final BlockingQueue<SynchronousQueue<MetaTraderMessage>> queuePool = new LinkedBlockingQueue<>();

    /**
     * Constructs an MT4Client.
     *
     * @param host           the hostname or IP of the MT4 server
     * @param port           the server port
     * @param messageHandler the handler for receiving messages and connection events
     */
    public MT4Client(String host, int port, MessageHandler messageHandler) {
        super(host, port);
        if (messageHandler == null) throw new IllegalArgumentException("messageHandler cannot be null");

        this.messageHandler = messageHandler;
        this.loginHelper = new LoginHelper(this);
        this.messageDecoder = new MessageDecoder(this, this);
    }

    /**
     * Connects to the MT4 server and performs login.
     */
    public void connect(int username, String password) throws IOException, InterruptedException {
        connect(username, password, false, true);
    }

    /**
     * Connects with control over blocking behavior.
     */
    public void connect(int username, String password, boolean waitConnection) throws IOException, InterruptedException {
        connect(username, password, waitConnection, true);
    }

    /**
     * Connects with full control over connection behavior.
     *
     * @param username        the account number
     * @param password        the password
     * @param waitConnection  whether to block until login and account data are ready
     * @param downloadSymbols whether to download symbol/group data after login
     */
    public void connect(int username, String password, boolean waitConnection, boolean downloadSymbols) throws IOException, InterruptedException {
        if (reading)
            throw new IllegalStateException("Connection is already in progress.");

        this.storedUsername = username;
        this.storedPassword = password;

        connect();
        loginHelper.login(username, password, false);

        // Post-login initialization must complete BEFORE the read thread starts.
        // MT4 uses a stateful stream decoder shared across all reads — concurrent
        // reads from postLoginInit and the command loop would corrupt decoder state.
        postLoginInit(downloadSymbols);

        threadsExecutors = MT4Constants.USE_VIRTUAL_THREADS
                ? Executors.newVirtualThreadPerTaskExecutor()
                : Executors.newCachedThreadPool();

        reading = true;

        // Start the read thread (command loop)
        threadsExecutors.execute(() -> {
            readThreadId = Thread.currentThread().threadId();
            log.debug("Starting read thread...");

            MT4Encryption encryption = loginHelper.getEncryption();

            // Reduce socket timeout for responsive read loop
            setSocketTimeout(2000);

            while (reading) {
                try {
                    byte[] cmdBuf = receiveDecoded(1, encryption);
                    byte cmd = cmdBuf[0];
                    log.debug("Received command: 0x{}", Integer.toHexString(cmd & 0xFF));
                    messageDecoder.onCommand(cmd, encryption);
                } catch (SocketTimeoutException e) {
                    // No data available; just continue
                } catch (DecoderException e) {
                    onDecoderException(e);
                } catch (RuntimeException e) {
                    log.error("Exception while reading message", e);
                } catch (IOException e) {
                    if (reading) {
                        disconnect();
                        messageHandler.onDisconnected(e, this);
                    }
                    break;
                }
            }
        });

        // Heartbeat thread
        threadsExecutors.execute(() -> {
            while (reading) {
                try {
                    Thread.sleep(4000);
                    if (!reading) break;
                    sendEncoded(new byte[]{MT4Constants.SEND_PING});
                    log.debug("Ping sent");
                } catch (IOException | InterruptedException e) {
                    if (reading) {
                        log.error("Heartbeat failed", e);
                    }
                }
            }
        });

        completeBaseAccountRead();

        if (waitConnection) {
            loginLatch.await(60, TimeUnit.SECONDS);
        }
    }

    /**
     * Performs post-login data initialization (before the read loop starts).
     */
    private void postLoginInit(boolean downloadSymbols) throws IOException {
        log.debug("Post-login initialization...");

        byte[][] srvResult = loginHelper.receiveServersList();
        serverInfo = ServerInfo.parse(srvResult[1], srvResult[0][0] != 0);

        if (downloadSymbols) {
            byte[] symbolBuf = loginHelper.receiveSymbols();
            symbolTable.init(symbolBuf);
            log.debug("Loaded {} symbols", symbolTable.size());
        }

        byte[] groupsBuf = loginHelper.receiveGroups();
        symbolTable.initGroups(groupsBuf);
        log.debug("Loaded {} symbol groups", symbolTable.getSymbolGroups() != null ? symbolTable.getSymbolGroups().length : 0);
        loginHelper.receiveMailHistory();

        // Order history MUST be read during login sequence (before createTransactionKey).
        // The original MT4 API always does this. Skipping it causes the server to reject
        // subsequent trade operations with error 133 (TRADE_DISABLED).
        int oneDayAgo = (int) (System.currentTimeMillis() / 1000 - 86400);
        int oneDayForward = (int) (System.currentTimeMillis() / 1000 + 86400);
        loginHelper.receiveOrderHistory(oneDayAgo, oneDayForward);

        loginHelper.createTransactionKey();
        byte[] accountBuf = loginHelper.receiveAccount();
        parseAccountData(accountBuf);
    }

    /**
     * Requests historical closed orders by opening a separate connection to the server.
     * Can be called at any time while connected — it does not interfere with the main
     * command loop because the order history protocol has no command prefix and uses
     * its own dedicated connection.
     *
     * @param fromTime start time as Unix timestamp
     * @param toTime   end time as Unix timestamp
     * @return the order history response containing closed trade records
     * @throws IOException if the connection or request fails
     */
    public OrderHistoryRes requestOrderHistory(int fromTime, int toTime) throws IOException {
        MT4Client side = new MT4Client(getHost(), getPort(), new MessageHandler() {
            @Override public void onMessage(MetaTraderMessage message, MT4Client c) {}
            @Override public void onReceiveFailure(DecoderException e) {}
            @Override public void onSendFailure(Exception e) {}
            @Override public void onConnected(MT4Client c) {}
            @Override public void onDisconnected(IOException exception, MT4Client c) {}
        });

        try {
            side.connect();
            side.loginHelper.login(storedUsername, storedPassword, false);

            // Send order history request
            OutputFactory of = side.outputFactoryPool.obtain();
            of.number(fromTime);
            of.number(toTime);
            byte[] packet = of.pack(MT4Constants.SEND_ORDER_HISTORY);
            side.sendEncoded(packet, side.loginHelper.getEncryption());
            side.byteArrayPool.release(packet);
            side.outputFactoryPool.release(of);

            // Read response
            MT4Encryption encryption = side.loginHelper.getEncryption();
            byte[] status = side.receiveDecoded(1, encryption);
            if (status[0] == 0) {
                byte[] data = side.readCompressed(encryption);
                return MessageDecoder.parseOrderHistory(data);
            } else if (status[0] == 1) {
                return new OrderHistoryRes(); // no records
            } else {
                throw new IOException("Order history request failed with code: " + (status[0] & 0xFF));
            }
        } finally {
            side.disconnect();
        }
    }

    /**
     * Disconnects the client from the server, shutting down all internal threads.
     */
    @Override
    public void disconnect() {
        try {
            super.disconnect();
        } catch (IOException e) {
            log.error("Error while disconnecting", e);
        } finally {
            reading = false;
            baseAccountRead = false;
            loginLatch = new CountDownLatch(1);
            if (threadsExecutors != null) threadsExecutors.shutdown();
        }
    }

    /**
     * Sends an encoded message to the server (thread-safe, excludes read thread).
     */
    public synchronized void sendEncoded(byte[] data) throws IOException {
        if (Thread.currentThread().threadId() == readThreadId) {
            throw new IllegalStateException("Cannot send message on the read thread. Post this action to your own thread.");
        }
        sendEncoded(data, loginHelper.getEncryption());
    }

    /**
     * Sends an asynchronous request.
     */
    public void sendRequestAsync(MetaTraderRequest request) {
        if (!baseAccountRead) throw new RuntimeException("Base account not yet read.");

        byte[] packet;
        if (request instanceof OrderTransactionReq otr) {
            otr.account = loginHelper.getAccount();
            packet = buildOrderTransactionPacket(otr);
        } else {
            OutputFactory outputFactory = outputFactoryPool.obtain();
            request.build(outputFactory);
            packet = outputFactory.pack(request.getCommand());
            outputFactoryPool.release(outputFactory);
        }

        try {
            sendEncoded(packet);
        } catch (Exception e) {
            log.error("Failed to send packet", e);
            threadsExecutors.execute(() -> messageHandler.onSendFailure(e));
        }
    }

    /**
     * Builds the full order transaction packet matching the MT4 protocol:
     * [command][92-byte payload][CRC32][requestId][random+SHA1+session+random]
     */
    private byte[] buildOrderTransactionPacket(OrderTransactionReq otr) {
        OutputFactory of = outputFactoryPool.obtain();
        otr.build(of);
        int coreSize = of.getOutputSize(); // 92 bytes

        // Build the OrderRequest: [cmd(1)] [core(92)] [crc32(4)] = 97 bytes
        byte[] orderReq = new byte[97];
        byte cmd = loginHelper.getServerBuild() > 1101
                ? MT4Constants.SEND_ORDER_TRANSACTION_V2
                : MT4Constants.SEND_ORDER_TRANSACTION;
        orderReq[0] = cmd;
        System.arraycopy(of.getRawBuffer(), 0, orderReq, 1, coreSize);
        outputFactoryPool.release(of);

        // Fill unused symbol bytes with random (offset 13 in orderReq = symbol start)
        fillRandom(orderReq, 13, 12);
        // Fill unused comment bytes with random (offset 57 in orderReq = comment start)
        fillRandom(orderReq, 57, 32);

        // CRC32 over bytes [1..92] with initial value
        int session = loginHelper.getSession();
        int account = loginHelper.getAccount();
        int crcInit = account + ((session >>> 8) & 0xFF);
        byte[] crcData = new byte[92];
        System.arraycopy(orderReq, 1, crcData, 0, 92);
        int crc32 = VCRC32.calculate(crcData, crcInit);
        BitConverter.putBytes(crc32, orderReq, 93);

        // Append RequestID: 97 + 4 = 101 bytes
        int requestId = requestIdCounter.getAndIncrement();
        byte[] req = new byte[101];
        System.arraycopy(orderReq, 0, req, 0, 97);
        BitConverter.putBytes(requestId, req, 97);

        if (session == 0) {
            return req;
        }

        // Compute verification data sizes
        long seed = (long)(account + ((session >>> 8) & 0xFF) + MT4Constants.CLIENT_BUILD + loginHelper.getServerBuild()) & 0xFFFFFFFFL;
        seed = (seed * 214013 + 2531011) & 0xFFFFFFFFL;
        int szRandOpen = (int) ((seed >>> 16) & 0x3) + 16;
        seed = (seed * 214013 + 2531011) & 0xFFFFFFFFL;
        int szRandClose = (int) ((seed >>> 16) & 0x7) + 8;

        int newSize = req.length + szRandOpen + 20 + 4 + szRandClose;
        byte[] data = new byte[newSize];
        System.arraycopy(req, 0, data, 0, req.length);

        // Random open padding
        for (int i = 0; i < szRandOpen; i++) {
            seed = (seed * 214013 + 2531011) & 0xFFFFFFFFL;
            data[i + req.length] = (byte) ((seed >>> 16) & 0xFF);
        }

        int szPack = req.length + szRandOpen;

        // SHA-1 verification hash
        byte[] bufHash = new byte[szPack - 1];
        System.arraycopy(data, 1, bufHash, 0, szPack - 1);
        VSHA1 sha = new VSHA1();
        sha.hashData(bufHash);
        sha.hashData(BitConverter.getBytes(account));
        sha.hashData(MT4Crypt.decode(loginHelper.getTransactionKey1(), MT4Crypt.getHardId()));
        sha.hashData(MT4Crypt.decode(loginHelper.getTransactionKey2(), MT4Crypt.getHardId()));
        sha.hashData(MT4Crypt.getHardId());
        System.arraycopy(sha.finalizeHash(), 0, data, szPack, 20);
        szPack += 20;

        // Session XOR ExeSize (4 bytes)
        BitConverter.putBytes(session ^ MT4Constants.CLIENT_EXE_SIZE, data, szPack);
        szPack += 4;

        // Random close padding
        for (int i = 0; i < szRandClose; i++) {
            seed = (seed * 214013 + 2531011) & 0xFFFFFFFFL;
            data[i + szPack] = (byte) ((seed >>> 16) & 0xFF);
        }

        return data;
    }

    /**
     * Fills unused bytes in symbol/comment fields with pseudo-random data.
     * Finds the first null byte after the string content and fills from there.
     */
    private static void fillRandom(byte[] buf, int fieldStart, int fieldLen) {
        int i;
        for (i = 0; i < fieldLen; i++) {
            if (buf[fieldStart + i] == 0) break;
        }
        long seed = System.currentTimeMillis() * 10000 + 621355968000000000L;
        for (i += 1; i < fieldLen; i++) {
            seed = (seed * 214013 + 2531011) & 0xFFFFFFFFL;
            buf[fieldStart + i] = (byte) ((seed >>> 16) & 0xFF);
        }
    }

    /**
     * Sends a synchronous request and waits for a response.
     */
    public MetaTraderMessage sendRequest(MetaTraderRequest request) {
        if (!baseAccountRead) throw new RuntimeException("Base account not yet read.");

        if (request.getExpectedResponseClass() == null) {
            log.warn("Sync request with no expected response; sending async.");
            sendRequestAsync(request);
            return null;
        }

        SynchronousQueue<MetaTraderMessage> queue = queuePool.poll();
        if (queue == null) {
            queue = new SynchronousQueue<>();
        }

        responseMap.computeIfAbsent(request.getExpectedResponseClass(), k -> new ConcurrentLinkedQueue<>()).add(queue);
        sendRequestAsync(request);

        MetaTraderMessage response;
        try {
            response = queue.poll(MT4Constants.MESSAGE_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            ConcurrentLinkedQueue<SynchronousQueue<MetaTraderMessage>> queues = responseMap.get(request.getExpectedResponseClass());
            if (queues != null) queues.remove(queue);
            queuePool.offer(queue);
        }

        return response;
    }

    @Override
    public void handleMessage(MetaTraderMessage message, MT4Client client) {
        if (message instanceof DisconnectEvent) {
            disconnect();
            messageHandler.onDisconnected(new IOException("Server decided to disconnect"), this);
            return;
        }

        // Check for sync response
        Class<? extends MetaTraderMessage> responseClass = message.getClass();
        ConcurrentLinkedQueue<SynchronousQueue<MetaTraderMessage>> queues = responseMap.get(responseClass);
        if (queues != null) {
            SynchronousQueue<MetaTraderMessage> queue = queues.poll();
            if (queue != null) {
                queue.offer(message);
                return;
            }
        }

        threadsExecutors.execute(() -> messageHandler.onMessage(message, client));
    }

    @Override
    public void onDecoderException(DecoderException e) {
        threadsExecutors.execute(() -> messageHandler.onReceiveFailure(e));
    }

    public void completeBaseAccountRead() {
        if (baseAccountRead) return;

        baseAccountRead = true;
        if (threadsExecutors != null) {
            threadsExecutors.execute(() -> messageHandler.onConnected(this));
        } else {
            messageHandler.onConnected(this);
        }
        loginLatch.countDown();
        shrinkMemory();
    }

    /**
     * Returns {@code true} if base account information has been fully loaded.
     */
    public boolean isBaseAccountRead() {
        return baseAccountRead;
    }

    /**
     * Returns the login helper for authentication and encryption handling.
     */
    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    /**
     * Returns the symbol table mapping symbol codes to names and metadata.
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    // ======================== Account Info ========================

    /** Returns account information received at login. */
    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    /** Returns a live map of open orders, keyed by ticket. Updated in real-time by server events. */
    public ConcurrentHashMap<Integer, OrderUpdateEvent> getOpenOrders() {
        return openOrders;
    }

    public double getBalance() { return accountInfo != null ? accountInfo.balance : 0; }
    public double getCredit() { return accountInfo != null ? accountInfo.credit : 0; }
    public int getLeverage() { return accountInfo != null ? accountInfo.leverage : 0; }
    public String getAccountName() { return accountInfo != null ? accountInfo.name : null; }
    public AccountType getAccountType() { return accountInfo != null ? accountInfo.accountType : null; }
    public boolean isInvestor() { return accountInfo != null && accountInfo.isInvestor(); }
    /** Returns the deposit currency of the connected account (e.g. "USD", "EUR"). */
    public String getAccountCurrency() { return accountInfo != null ? accountInfo.currency : null; }
    /** Returns the broker company name. */
    public String getCompany() { return accountInfo != null ? accountInfo.company : null; }
    /** Returns the account group name. */
    public String getAccountGroup() { return accountInfo != null ? accountInfo.group : null; }
    /** Returns the broker/server information parsed during login. */
    public ServerInfo getServerInfo() { return serverInfo; }

    /**
     * Called by {@link MessageDecoder} to keep the open orders map synchronized with live updates.
     */
    public void updateOpenOrder(OrderUpdateEvent event) {
        if (event.ticket == 0) return;

        if (event.action == UpdateAction.PositionOpen || event.action == UpdateAction.PendingOpen
                || event.action == UpdateAction.PositionModify || event.action == UpdateAction.PendingModify
                || event.action == UpdateAction.PendingFill) {
            openOrders.put(event.ticket, event);
        } else if (event.action == UpdateAction.PositionClose || event.action == UpdateAction.PendingClose) {
            openOrders.remove(event.ticket);
        }

        // Update balance/credit from the latest server snapshot
        if (accountInfo != null) {
            accountInfo.balance = event.balance;
            accountInfo.credit = event.credit;
        }
    }

    /**
     * Parses the account response buffer received during login.
     */
    private void parseAccountData(byte[] data) {
        if (data == null || data.length < 0x60) return;

        accountInfo = new AccountInfo();
        accountInfo.accountType = AccountType.forValue(BitConverter.toInt32(data, 0x00));
        accountInfo.name = BitConverter.getString(data, 0x04, 64);
        accountInfo.leverage = BitConverter.toInt32(data, 0x44);
        accountInfo.balance = BitConverter.toDouble(data, 0x48);
        accountInfo.credit = BitConverter.toDouble(data, 0x50);

        // Parse ConGroup fields from offset 0x58
        // ConGroup.group is at ConGroup+0 (16 bytes)
        // ConGroup.company is at ConGroup+28 (128 bytes)
        // ConGroup.currency is at ConGroup+13468 (12 bytes)
        int conGroupOffset = 0x58;
        if (data.length > conGroupOffset + 13480) {
            accountInfo.group = BitConverter.getString(data, conGroupOffset, 16);
            accountInfo.company = BitConverter.getString(data, conGroupOffset + 28, 128);
            accountInfo.currency = BitConverter.getString(data, conGroupOffset + 13468, 12);
        }

        if (data.length > 0x35E1) {
            accountInfo.accountMode = BitConverter.toInt32(data, 0x35D8);
            accountInfo.timeDst = data[0x35E0];
            accountInfo.timeZone = data[0x35E1];
        }

        log.debug("Account: {}", accountInfo);

        // Parse open orders from the buffer tail
        int ordersOffset;
        short serverBuild = loginHelper.getServerBuild();
        if (serverBuild >= 1350) {
            ordersOffset = 0x3624;
        } else if (serverBuild >= 1290) {
            ordersOffset = 0x3620;
        } else {
            ordersOffset = 0x3600;
        }

        openOrders.clear();
        while (ordersOffset + 224 <= data.length) {
            int ticket = BitConverter.toInt32(data, ordersOffset);
            if (ticket == 0) break;

            OrderUpdateEvent order = new OrderUpdateEvent();
            MessageDecoder.parseOrderRecord(order, data, ordersOffset);
            order.balance = accountInfo.balance;
            order.credit = accountInfo.credit;
            order.action = (order.type == Op.Buy || order.type == Op.Sell)
                    ? UpdateAction.PositionOpen : UpdateAction.PendingOpen;
            openOrders.put(order.ticket, order);
            ordersOffset += 224;
        }

        log.debug("Loaded {} open orders", openOrders.size());
    }

    @Override
    public void shrinkMemory() {
        super.shrinkMemory();
    }

}
