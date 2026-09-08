package pro.mrpc.mt4.utils;

import pro.mrpc.mt4.MT4Client;
import pro.mrpc.mt4.crypt.EasyCrypt;
import pro.mrpc.mt4.crypt.MT4Crypt;
import pro.mrpc.mt4.crypt.MT4Encryption;
import pro.mrpc.mt4.network.OutputFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles the complete MT4 login/authentication flow:
 * 1. Send login request with easy-crypt obfuscation
 * 2. Receive server build/session, derive key via 16-case key derivation loop
 * 3. LoginIdentification - decode LoginId, LoginIdData, LoginIdEx from server
 * 4. TerminalIdentification - send shuffled packet sequence to prove identity
 * 5. Post-login requests: symbols, groups, account info, etc.
 */
public class LoginHelper {
    private static final Logger log = LoggerFactory.getLogger(LoginHelper.class);

    public List<String> loginIdServerUrls = new ArrayList<>(Arrays.asList("https://loginid-mt4.mrpc.pro"));
    public String guid = "1288942f-aadb-4d98-8cc1-c06f33730d76";
    public int loginIdWebServerTimeout = 10000;

    private MT4Encryption encryption;
    private short serverBuild;
    private int session;
    private int account;
    private long loginId;
    private long loginIdEx;
    private long loginIdData;
    private byte[] transactionKey1 = new byte[16];
    private byte[] transactionKey2 = new byte[16];

    // State for TerminalIdentification
    private int seed;
    private byte[] hashKey;

    public final MT4Client client;

    public LoginHelper(MT4Client client) {
        this.client = client;
    }

    public void login(int username, String password, boolean dataCenter) throws IOException {
        log.debug("Performing MT4 login...");

        byte[] pasBytes = password.getBytes(StandardCharsets.US_ASCII);
        if (pasBytes.length > 15) {
            pasBytes = Arrays.copyOf(pasBytes, 15);
        }
        byte[] key = MD5.computeMD5(pasBytes);
        encryption = new MT4Encryption(key);

        byte[] loginRequest = createLoginRequest(key, username, dataCenter);
        EasyCrypt.encrypt(loginRequest);
        client.sendRaw(loginRequest);

        // Receive login response
        ByteArray raw = client.receiveRaw(1);
        if (raw.items[0] != 0) {
            throw new IOException("Login failed with error code: " + (raw.items[0] & 0xFF));
        }

        // Reset encoder/decoder after successful login byte
        encryption.reset();

        // Read server version, build, and session
        byte[] buf = receiveDecoded(1); // unknown byte
        buf = receiveDecoded(2);
        serverBuild = BitConverter.toInt16(buf, 0);
        log.debug("Server build: {}", serverBuild);

        buf = receiveDecoded(4);
        session = BitConverter.toInt32(buf, 0);
        account = username;

        if (serverBuild < 950) {
            log.debug("Old server version, skipping identification");
            return;
        }

        if (dataCenter || session == 0) {
            return;
        }

        // Key derivation loop (8 iterations, 16 possible actions)
        buf = receiveDecoded(64);
        int deriveSeed = (key[3] + key[2] + key[1] + key[0] + ((session >>> 8) & 0xFF) + account + MT4Constants.CLIENT_BUILD);
        byte[] derivedKey = key;

        for (int i = 0; i < 8; i++) {
            deriveSeed = deriveSeed * 214013 + 2531011;
            int action = ((deriveSeed >>> 16) & 0xF);
            derivedKey = switch (action) {
                case 0 -> modifyKeySHA256_MTComplex(buf, derivedKey);
                case 1 -> modifyKeySHA256_MTLogin(derivedKey);
                case 2 -> modifyKeySHA256_MTType(derivedKey);
                case 3 -> modifyKeyMD5_MTRandom(buf, derivedKey);
                case 4 -> modifyKeySwapPair(derivedKey);
                case 5 -> modifyKeySHA1_MTType(derivedKey);
                case 6 -> modifyKeyNotEven(derivedKey);
                case 7 -> modifyKeyNotOdd(derivedKey);
                case 8 -> modifyKeySHA256_MTBuild(derivedKey);
                case 9 -> modifyKeyEasyKey(derivedKey);
                case 10 -> modifyKeySwapAllBytes(derivedKey);
                case 11 -> modifyKeyMD5_MTComplex(buf, derivedKey);
                case 12 -> modifyKeySHA256_MTRandom(buf, derivedKey);
                case 13 -> modifyKeyNotBytes(derivedKey);
                case 14 -> modifyKeySHA1_MTComplex(buf, derivedKey);
                case 15 -> modifyKeySHA256_MTSession(derivedKey);
                default -> derivedKey;
            };
        }

        loginIdentification(derivedKey);
        encryption.changeKey(derivedKey);
        // Do NOT reset encoder/decoder state here — the rolling XOR cipher
        // must continue from where LoginIdentification left off.
        terminalIdentification(derivedKey);
    }

    // ======================== Login Identification ========================

    private void loginIdentification(byte[] hashKey) throws IOException {
        loginId = 0;
        if (serverBuild <= 1101) return;

        byte[] hdrBuf = receiveDecoded(8);
        int sizeData = BitConverter.toInt16(hdrBuf, 0) & 0xFFFF;
        short packType = BitConverter.toInt16(hdrBuf, 2);
        short dataType = BitConverter.toInt16(hdrBuf, 4);

        byte[] data = receiveDecoded(sizeData);
        byte[] multipack = decryptPacket(data, hashKey, sizeData, dataType);

        int start = 0;
        while (start < multipack.length && multipack.length - start >= 8) {
            byte[] subHdr = new byte[8];
            System.arraycopy(multipack, start, subHdr, 0, 8);
            start += 8;

            byte[] decHdr = MT4Crypt.decode(subHdr, hashKey);
            int subSize = BitConverter.toInt16(decHdr, 0) & 0xFFFF;
            short subPackType = BitConverter.toInt16(decHdr, 2);
            short subDataType = BitConverter.toInt16(decHdr, 4);

            if (multipack.length - start < subSize) break;

            byte[] packBytes = new byte[subSize];
            System.arraycopy(multipack, start, packBytes, 0, subSize);
            start += subSize;

            byte[] pack = decryptPacket(packBytes, hashKey, subSize, subDataType);

            if (subPackType == MT4Constants.LOGIN_ID_TYPE) {
                if (serverBuild >= 1435) {
                    loginId = getLoginId(loginIdServerUrls, pack, "DecodeNew");
                } else {
                    loginId = decodeLoginIdLocal(pack);
                }
                loginId ^= ((long) account << 32) + session;
                loginId ^= MT4Constants.LOGIN_XOR_CONSTANT;
                log.debug("LoginId: {}", loginId);
            } else if (subPackType == MT4Constants.LOGIN_ID_DATA_TYPE) {
                loginIdData = new LoginId().decodeData(pack);
                loginIdData ^= ((long) account << 32) + session;
                loginIdData ^= MT4Constants.LOGIN_XOR_CONSTANT;
                log.debug("LoginIdData: {}", loginIdData);
            } else if (subPackType == MT4Constants.LOGIN_ID_EX_TYPE) {
                loginIdEx = getLoginId(loginIdServerUrls, pack, "DecodeEx");
                loginIdEx ^= ((long) account << 32) + session;
                if (serverBuild >= 1435) loginIdEx ^= MT4Constants.LOGIN_ID_EX_XOR;
                loginIdEx ^= MT4Constants.LOGIN_XOR_CONSTANT;
                log.debug("LoginIdEx: {}", loginIdEx);
            }
        }
    }

    private long decodeLoginIdLocal(byte[] pack) {
        return new LoginId().decode(pack);
    }

    private long getLoginId(List<String> urls, byte[] loginHash, String function) throws IOException {
        LoginIdWebServer lid = new LoginIdWebServer();
        long res = 0;
        boolean got = false;
        StringBuilder errors = new StringBuilder();
        for (String url : urls) {
            try {
                res = lid.decode(url + "/" + function, guid, loginHash, loginIdWebServerTimeout);
                got = true;
                break;
            } catch (Exception e) {
                e.printStackTrace();
                errors.append(e.getMessage()).append("(").append(url).append("); ");
            }
        }
        if (!got) {
            throw new RuntimeException("Cannot receive LoginID: " + errors);
        }
        return res;
    }

    // ======================== Terminal Identification ========================

    private void terminalIdentification(byte[] hashKey) throws IOException {
        seed = account + session;
        byte[] rnd = new byte[64];
        for (int i = 0; i < 64; i++) {
            seed = seed * 214013 + 2531011;
            rnd[i] = (byte) ((seed >>> 16) & 0xFF);
        }
        this.hashKey = modifyKeySHA1_MTComplex(rnd, hashKey);
        byte[] packKey = Arrays.copyOf(this.hashKey, this.hashKey.length);

        boolean oldBuild = serverBuild <= 1101;
        PacketBuilder[] builders;
        if (oldBuild) {
            builders = new PacketBuilder[]{
                    this::buildDataPacket, this::buildHashPacket,
                    this::buildModifyKeyPacket, this::buildRandomPacket,
                    this::buildModifyKeyPacket, this::buildRandomPacket,
                    this::buildModifyKeyPacket, this::buildRandomPacket
            };
        } else {
            builders = new PacketBuilder[]{
                    this::buildDataPacket, this::buildHashPacket,
                    this::buildLoginIdPacket,
                    this::buildModifyKeyPacket, this::buildRandomPacket,
                    this::buildModifyKeyPacket, this::buildRandomPacket,
                    this::buildModifyKeyPacket, this::buildRandomPacket
            };
        }

        seed = seed * 214013 + 2531011;
        int step = ((seed >>> 16) & 3) + (oldBuild ? 4 : 5);

        // Shuffle the packet builders
        for (int i = 0; i < 128; i++) {
            seed = seed * 214013 + 2531011;
            int ind1 = (int) (((seed >>> 16) & 0x7FFF) % step);
            seed = seed * 214013 + 2531011;
            int ind2 = (int) (((seed >>> 16) & 0x7FFF) % step);
            PacketBuilder tmp = builders[ind1];
            builders[ind1] = builders[ind2];
            builders[ind2] = tmp;
        }

        // Build all packets
        byte[][] parts = new byte[step][];
        int totalLen = 0;
        for (int i = 0; i < step; i++) {
            parts[i] = builders[i].build();
            totalLen += parts[i].length;
        }

        byte[] data = new byte[totalLen];
        int offset = 0;
        for (int i = 0; i < step; i++) {
            System.arraycopy(parts[i], 0, data, offset, parts[i].length);
            offset += parts[i].length;
        }

        // Build outer header
        byte[] hdr = new byte[8];
        BitConverter.putBytes((short) data.length, hdr, 0);
        hdr[2] = 2; // packType = 2 (cleartext header)
        hdr[4] = 1; // dataType = 1 (AES)
        seed = seed * 214013 + 2531011;
        BitConverter.putBytes((short) ((seed >>> 16) & 0x7FFF), hdr, 6);

        byte[] encrypted = cryptPacket(hdr, data, packKey);
        byte[] packet = new byte[1 + encrypted.length];
        packet[0] = MT4Constants.SEND_TERMINAL_ID;
        System.arraycopy(encrypted, 0, packet, 1, encrypted.length);

        client.sendRaw(packet);
    }

    @FunctionalInterface
    private interface PacketBuilder {
        byte[] build();
    }

    private byte[] buildDataPacket() {
        byte[] hdr = new byte[8];
        hdr[4] = 1; // dataType = AES
        seed = seed * 214013 + 2531011;
        BitConverter.putBytes((short) ((seed >>> 16) & 0x7FFF), hdr, 6);

        byte[] data;
        if (serverBuild > 1294) {
            hdr[0] = 68;
            data = new byte[68];
        } else {
            hdr[0] = 60;
            data = new byte[60];
        }

        System.arraycopy(MT4Crypt.getHardId(), 0, data, 0, 16);
        BitConverter.putBytes(MT4Constants.CLIENT_EXE_SIZE, data, 20);
        BitConverter.putBytes(MT4Constants.CLIENT_EXE_HASH, data, 24);

        if (serverBuild > 1294) {
            long softId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
            BitConverter.putBytes(softId, data, 60);
        }

        return cryptPacket(hdr, data, hashKey);
    }

    private byte[] buildHashPacket() {
        byte[] sha1Hash = SHA1.computeHash(
                MT4Crypt.getHardId(),
                BitConverter.getBytes(account),
                encryption.getKey(),
                hashKey
        );

        System.arraycopy(hashKey, 0, transactionKey1, 0, hashKey.length);
        transactionKey1 = MT4Crypt.encode(transactionKey1, sha1Hash);
        transactionKey1 = MT4Crypt.encode(transactionKey1, MT4Crypt.getHardId());

        byte[] hash2 = SHA1.computeHash(
                BitConverter.getBytes(account),
                encryption.getKey(),
                BitConverter.getBytes(session)
        );

        byte[] hdr = new byte[8];
        hdr[0] = 20; // size
        hdr[2] = 1;  // packType = 1 (XOR header)
        seed = seed * 214013 + 2531011;
        BitConverter.putBytes((short) ((seed >>> 16) & 0x7FFF), hdr, 6);
        return cryptPacket(hdr, hash2, hashKey);
    }

    private byte[] buildLoginIdPacket() {
        // LoginId
        byte[] hdr = new byte[8];
        hdr[0] = 8;
        hdr[2] = 10; // packType = 10
        BitConverter.putBytes((short) ThreadLocalRandom.current().nextInt(), hdr, 6);
        byte[] data = BitConverter.getBytes(loginId ^ MT4Constants.LOGIN_XOR_CONSTANT);
        byte[] result = encryptPacket(hdr, data, hashKey);

        if (MT4Constants.CLIENT_BUILD > 1321) {
            // LoginIdData
            hdr = new byte[8];
            hdr[0] = 8;
            hdr[2] = 12;
            BitConverter.putBytes((short) ThreadLocalRandom.current().nextInt(), hdr, 6);
            data = BitConverter.getBytes(loginIdData ^ MT4Constants.LOGIN_XOR_CONSTANT);
            byte[] r = encryptPacket(hdr, data, hashKey);
            result = concat(result, r);
        }

        if (MT4Constants.CLIENT_BUILD > 1393) {
            // LoginIdEx
            hdr = new byte[8];
            hdr[0] = 8;
            hdr[2] = 16;
            BitConverter.putBytes((short) ThreadLocalRandom.current().nextInt(), hdr, 6);
            data = BitConverter.getBytes(loginIdEx ^ MT4Constants.LOGIN_XOR_CONSTANT);
            byte[] r = encryptPacket(hdr, data, hashKey);
            result = concat(result, r);
        }

        return result;
    }

    private byte[] buildModifyKeyPacket() {
        byte[] hdr = new byte[8];
        seed = seed * 214013 + 2531011;
        int cnt = ((seed >>> 16) & 0x1F) + 8;
        BitConverter.putBytes((short) cnt, hdr, 0);
        hdr[2] = 3; // packType = 3 (modify key)
        seed = seed * 214013 + 2531011;
        BitConverter.putBytes((short) ((seed >>> 16) & 0x7FFF), hdr, 6);

        byte[] data = new byte[cnt];
        for (int i = 0; i < cnt; i++) {
            seed = seed * 214013 + 2531011;
            data[i] = (byte) ((seed >>> 16) & 0xFF);
        }
        byte[] pack = cryptPacket(hdr, data, hashKey);
        hashKey = MT4Crypt.encode(hashKey, data);
        return pack;
    }

    private byte[] buildRandomPacket() {
        byte[] hdr = new byte[8];
        seed = seed * 214013 + 2531011;
        int cnt = ((seed >>> 16) & 0x1F) + 8;
        BitConverter.putBytes((short) cnt, hdr, 0);
        hdr[2] = 4; // packType = 4 (random)
        seed = seed * 214013 + 2531011;
        BitConverter.putBytes((short) ((seed >>> 16) & 0x7FFF), hdr, 6);

        byte[] data = new byte[cnt];
        for (int i = 0; i < cnt; i++) {
            seed = seed * 214013 + 2531011;
            data[i] = (byte) ((seed >>> 16) & 0xFF);
        }
        return cryptPacket(hdr, data, hashKey);
    }

    // ======================== Packet Encryption ========================

    private byte[] cryptPacket(byte[] hdr, byte[] data, byte[] key) {
        int length = BitConverter.toInt16(hdr, 0) & 0xFFFF;
        boolean hasData = length >= 1 && data.length != 0;

        byte[] encData = data;
        if (hasData) {
            int typeData = BitConverter.toInt16(hdr, 4);
            if (typeData == 0) {
                encData = MT4Crypt.encode(data, key);
            } else if (typeData == 1) {
                encData = encryptAesWithTail(data, key, length);
            }
        }

        byte[] encHdr;
        int typePack = BitConverter.toInt16(hdr, 2);
        if (typePack == 2) {
            encHdr = Arrays.copyOf(hdr, hdr.length);
        } else {
            encHdr = MT4Crypt.encode(hdr, key);
        }

        if (!hasData) return encHdr;
        return concat(encHdr, encData);
    }

    private byte[] encryptPacket(byte[] hdr, byte[] data, byte[] key) {
        return cryptPacket(hdr, data, key);
    }

    private byte[] decryptPacket(byte[] data, byte[] key, int sizeData, short dataType) {
        if (dataType == 0) {
            return MT4Crypt.decode(data, key);
        } else if (dataType == 1) {
            return decryptAesWithTail(data, key, sizeData);
        }
        return data;
    }

    private byte[] encryptAesWithTail(byte[] data, byte[] key, int length) {
        int szData = length & ~0xF;
        vAES aes = new vAES();
        byte[] mainPart = Arrays.copyOf(data, szData);
        byte[] encMain = aes.EncryptData(mainPart, key);
        int tailLen = length - szData;
        if (tailLen > 0) {
            byte[] tail = new byte[tailLen];
            System.arraycopy(data, szData, tail, 0, tailLen);
            byte[] encTail = MT4Crypt.encode(tail, key);
            return concat(encMain, encTail);
        }
        return encMain;
    }

    private byte[] decryptAesWithTail(byte[] data, byte[] key, int sizeData) {
        int szData = sizeData & ~0xF;
        int szTail = sizeData - szData;
        vAES aes = new vAES();
        byte[] mainPart = Arrays.copyOf(data, szData);
        byte[] decMain = aes.DecryptData(mainPart, key);
        if (szTail > 0) {
            byte[] tail = new byte[szTail];
            System.arraycopy(data, szData, tail, 0, szTail);
            byte[] decTail = MT4Crypt.decode(tail, key);
            return concat(decMain, decTail);
        }
        return decMain;
    }

    // ======================== Key Derivation Functions ========================

    private byte[] modifyKeySHA256_MTComplex(byte[] rcvData, byte[] hashKey) {
        byte[] data = new byte[97];
        data[0] = 0;
        copyAscii("MTComplex", data, 1);
        System.arraycopy(rcvData, 0, data, 11, rcvData.length);
        BitConverter.putBytes(MT4Constants.CLIENT_BUILD, data, 75);
        System.arraycopy(hashKey, 0, data, 77, hashKey.length);
        BitConverter.putBytes(account, data, 93);
        return SHA256.computeKey(data);
    }

    private byte[] modifyKeySHA256_MTLogin(byte[] hashKey) {
        byte[] data = new byte[28];
        BitConverter.putBytes(account, data, 0);
        System.arraycopy(hashKey, 0, data, 4, hashKey.length);
        copyAscii("MTLogin", data, 20);
        return SHA256.computeKey(data);
    }

    private byte[] modifyKeySHA256_MTType(byte[] hashKey) {
        byte[] data = new byte[24];
        data[0] = 0;
        System.arraycopy(hashKey, 0, data, 1, hashKey.length);
        copyAscii("MTType", data, 17);
        return SHA256.computeKey(data);
    }

    private byte[] modifyKeyMD5_MTRandom(byte[] rcvData, byte[] hashKey) {
        byte[] data = new byte[89];
        System.arraycopy(hashKey, 0, data, 0, hashKey.length);
        System.arraycopy(rcvData, 0, data, 16, rcvData.length);
        copyAscii("MTRandom", data, 80);
        return MD5.computeMD5(data);
    }

    private byte[] modifyKeySwapPair(byte[] hashKey) {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i += 2) {
            key[i] = hashKey[i + 1];
            key[i + 1] = hashKey[i];
        }
        return key;
    }

    private byte[] modifyKeySHA1_MTType(byte[] hashKey) {
        byte[] data = new byte[24];
        copyAscii("MTType", data, 0);
        System.arraycopy(hashKey, 0, data, 7, hashKey.length);
        data[23] = 0;
        return SHA1.computeKey(data);
    }

    private byte[] modifyKeyNotEven(byte[] hashKey) {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i += 2) {
            key[i] = (byte) (~hashKey[i]);
            key[i + 1] = hashKey[i + 1];
        }
        return key;
    }

    private byte[] modifyKeyNotOdd(byte[] hashKey) {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i += 2) {
            key[i] = hashKey[i];
            key[i + 1] = (byte) (~hashKey[i + 1]);
        }
        return key;
    }

    private byte[] modifyKeySHA256_MTBuild(byte[] hashKey) {
        byte[] data = new byte[26];
        BitConverter.putBytes(MT4Constants.CLIENT_BUILD, data, 0);
        System.arraycopy(hashKey, 0, data, 2, hashKey.length);
        copyAscii("MTBuild", data, 18);
        return SHA256.computeKey(data);
    }

    private byte[] modifyKeyEasyKey(byte[] hashKey) {
        byte[] ecKey = MT4Crypt.getEasyCryptKey();
        byte last = 0;
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) (hashKey[i] ^ ((ecKey[i] & 0xFF) + (last & 0xFF)));
            last = key[i];
        }
        return key;
    }

    private byte[] modifyKeySwapAllBytes(byte[] hashKey) {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = hashKey[15 - i];
        }
        return key;
    }

    private byte[] modifyKeyMD5_MTComplex(byte[] rcvData, byte[] hashKey) {
        byte[] data = new byte[97];
        data[0] = 0;
        copyAscii("MTComplex", data, 1);
        BitConverter.putBytes(MT4Constants.CLIENT_BUILD, data, 11);
        System.arraycopy(hashKey, 0, data, 13, hashKey.length);
        System.arraycopy(rcvData, 0, data, 29, rcvData.length);
        BitConverter.putBytes(account, data, 93);
        return MD5.computeMD5(data);
    }

    private byte[] modifyKeySHA256_MTRandom(byte[] rcvData, byte[] hashKey) {
        byte[] data = new byte[89];
        copyAscii("MTRandom", data, 0);
        System.arraycopy(hashKey, 0, data, 9, hashKey.length);
        System.arraycopy(rcvData, 0, data, 25, rcvData.length);
        return SHA256.computeKey(data);
    }

    private byte[] modifyKeyNotBytes(byte[] hashKey) {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) (~hashKey[i]);
        }
        return key;
    }

    private byte[] modifyKeySHA1_MTComplex(byte[] rcvData, byte[] hashKey) {
        byte[] data = new byte[97];
        data[0] = 0;
        BitConverter.putBytes(MT4Constants.CLIENT_BUILD, data, 1);
        System.arraycopy(rcvData, 0, data, 3, rcvData.length);
        System.arraycopy(hashKey, 0, data, 67, hashKey.length);
        BitConverter.putBytes(account, data, 83);
        copyAscii("MTComplex", data, 87);
        return SHA1.computeKey(data);
    }

    private byte[] modifyKeySHA256_MTSession(byte[] hashKey) {
        byte[] data = new byte[30];
        BitConverter.putBytes(session, data, 0);
        System.arraycopy(hashKey, 0, data, 4, hashKey.length);
        copyAscii("MTSession", data, 20);
        return SHA256.computeKey(data);
    }

    // ======================== Login Request Construction ========================

    private byte[] createLoginRequest(byte[] key, int username, boolean dataCenter) {
        byte[] login = new byte[28];
        byte[] encodedKey = MT4Crypt.decode(key, key);
        short ver = 400;
        login[0] = (byte) (dataCenter ? 0xB : 0);
        login[1] = (byte) (login[0] + username + login[27] + MT4Constants.CLIENT_BUILD);
        System.arraycopy(encodedKey, 0, login, 3, encodedKey.length);
        BitConverter.putBytes(username, login, 19);
        BitConverter.putBytes(ver, login, 23);
        BitConverter.putBytes(MT4Constants.CLIENT_BUILD, login, 25);
        return login;
    }

    // ======================== Post-Login Requests ========================

    /**
     * Sends post-login requests: servers list, symbols, groups, mail, account info.
     */
    public byte[] receiveSymbols() throws IOException {
        byte[] buf = new byte[]{0x8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        sendEncoded(buf);
        buf = receiveDecoded(1);
        if (buf[0] != 0) {
            throw new IOException("Symbols request failed with code: " + (buf[0] & 0xFF));
        }
        return client.readCompressed(encryption);
    }

    /**
     * Receives the servers list from the MT4 server.
     *
     * @return a two-element array: [0] = is_demo flag (single byte), [1] = raw server list data
     */
    public byte[][] receiveServersList() throws IOException {
        byte[] buf = new byte[]{0x10};
        sendEncoded(buf);
        buf = receiveDecoded(1);
        if (buf[0] != 0) {
            throw new IOException("Servers list request failed");
        }
        byte[] isDemoBuf = receiveDecoded(1);
        byte[] data = client.readCompressed(encryption);
        return new byte[][] { isDemoBuf, data };
    }

    public byte[] receiveGroups() throws IOException {
        byte[] buf = new byte[]{0xA, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        sendEncoded(buf);
        buf = receiveDecoded(1);
        if (buf[0] != 0) {
            throw new IOException("Groups request failed");
        }
        return client.readCompressed(encryption);
    }

    /**
     * Reads closed order history during initial connection setup.
     * The original MT4 API always reads order history as part of the login sequence,
     * between receiveMailHistory and createTransactionKey.
     */
    public void receiveOrderHistory(int fromTime, int toTime) throws IOException {
        byte[] buf = new byte[9];
        buf[0] = 0x22;
        BitConverter.putBytes(fromTime, buf, 1);
        BitConverter.putBytes(toTime, buf, 5);
        sendEncoded(buf);
        buf = receiveDecoded(1);
        if (buf[0] == 0) {
            client.readCompressed(encryption); // read and discard
        }
        // status 1 = no records, no further data
    }

    public byte[] receiveMailHistory() throws IOException {
        byte[] buf = new byte[]{0x21};
        sendEncoded(buf);
        buf = receiveDecoded(1);
        if (buf[0] == 0) {
            buf = receiveDecoded(4);
            int numRecords = BitConverter.toInt32(buf, 0);
            if (numRecords >= 1 && numRecords <= 1024) {
                return receiveDecoded(numRecords * 0x44);
            }
        }
        return null;
    }

    public byte[] receiveAccount() throws IOException {
        OutputFactory of = client.outputFactoryPool.obtain();
        of.single(MT4Constants.SEND_ACCOUNT);
        of.number(0); // news mode
        of.number(338067L); // news req (m_NewsReq)
        of.number(2); // s8

        // Build update random
        byte[] ur = updateRandom(session);
        of.array(ur);

        // 12 random bytes
        Random rnd = new Random();
        for (int i = 0; i < 12; i++) {
            of.single((byte) rnd.nextGaussian());
        }

        // Application info (terminal identification string)
        String info = "file=terminal.exe\tversion=" +
                MT4Constants.CLIENT_BUILD +
                "\tcert_company=MetaQuotes Ltd" +
                "\tcert_issuer=DigiCert Trusted G4 Code Signing RSA4096 SHA384 2021 CA1" +
                "\tcert_serial=4750d468172c05d7a106895f4c0a3904" +
                "\tos_ver=Windows 11 build 22" + randomNumericString(3) +
                "\tos_id=" + randomNumericString(4) + "-" + randomNumericString(4) + "-" +
                randomNumericString(4) + "-AAOEM" +
                "\tdomain=\tcomputer=" + randomString(4) + "\t\0";

        // Match original encoding: getBytes("UNICODE") returns UTF-16 with 2-byte BOM,
        // then strip 3 bytes from the start. For ASCII this coincidentally produces UTF-16LE
        // but 1 byte shorter (the last byte of the final \0 is truncated).
        byte[] unicodeBytes = info.getBytes(StandardCharsets.UTF_16);
        byte[] infoBytes = new byte[unicodeBytes.length - 3];
        System.arraycopy(unicodeBytes, 3, infoBytes, 0, infoBytes.length);

        // Inner sub-packet: sub-header + info bytes
        int innerSize = 8 + infoBytes.length; // sub-header(8) + data

        // Outer header wraps the inner sub-packet
        of.number((short) innerSize);
        of.number((short) 2);  // packType = 2
        of.number((short) 0);  // dataType
        of.number((short) 0);  // random

        // Inner sub-header
        of.number((short) infoBytes.length);
        of.number((short) 13); // packType = 13 (app info)
        of.number((short) 0);  // dataType
        of.number((short) 0);  // random

        of.array(infoBytes);

        byte[] packet = of.packRaw();
        client.outputFactoryPool.release(of);

        sendEncoded(packet);
        client.byteArrayPool.release(packet);

        byte[] res = receiveDecoded(1);
        if (res[0] != 0) {
            throw new IOException("Account request failed with code: " + (res[0] & 0xFF));
        }
        return client.readCompressed(encryption);
    }

    public void createTransactionKey() {
        byte[] rnd = new byte[16];
        int seed = account + session;
        for (int i = 0; i < 16; i++) {
            seed = seed * 214013 + 2531011;
            rnd[i] = (byte) ((seed >>> 16) & 0xFF);
        }
        byte[] data = new byte[36];
        System.arraycopy(rnd, 0, data, 0, rnd.length);
        System.arraycopy(MT4Crypt.getHardId(), 0, data, 16, 16);
        BitConverter.putBytes(MT4Constants.CLIENT_EXE_SIZE, data, 32);
        byte[] hash = MD5.computeMD5(data);
        transactionKey2 = MT4Crypt.encode(rnd, hash);
        transactionKey2 = MT4Crypt.encode(transactionKey2, MT4Crypt.getHardId());
    }

    public void sendPing() throws IOException {
        sendEncoded(new byte[]{MT4Constants.SEND_PING});
    }

    // ======================== Utility ========================

    private void sendEncoded(byte[] data) throws IOException {
        client.sendEncoded(data, encryption);
    }

    private byte[] receiveDecoded(int count) throws IOException {
        return client.receiveDecoded(count, encryption);
    }

    private byte[] updateRandom(long id) {
        byte[] buf = new byte[48];
        int offset = 0;
        int remaining = 48;
        while (remaining > 0) {
            id += 0xA0761D6478BD642FL;
            long v = id ^ 0xE7037ED1A0B428DBL;
            long m1 = makeULongLong(v) * v;
            long m2 = makeULongLong(id) * id;
            long rnd = m2 ^ makeULongLong(m1);
            int size = Math.min(remaining, 8);
            byte[] bytes = BitConverter.getBytes(rnd);
            System.arraycopy(bytes, 0, buf, offset, size);
            offset += size;
            remaining -= size;
        }
        return buf;
    }

    private long makeULongLong(long v) {
        return ((v >>> 32) & 0xFFFFFFFFL) | ((v & 0xFFFFFFFFL) << 32);
    }

    private static void copyAscii(String s, byte[] dest, int offset) {
        byte[] bytes = s.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(bytes, 0, dest, offset, bytes.length);
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private static String randomNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    // ======================== Getters ========================

    public MT4Encryption getEncryption() {
        return encryption;
    }

    public short getServerBuild() {
        return serverBuild;
    }

    public int getSession() {
        return session;
    }

    public int getAccount() {
        return account;
    }

    public byte[] getTransactionKey1() {
        return transactionKey1;
    }

    public byte[] getTransactionKey2() {
        return transactionKey2;
    }

}
