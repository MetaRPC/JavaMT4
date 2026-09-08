package pro.mrpc.mt4.utils;

public class MT4Constants {
    /** Use Java Virtual Threads for concurrent non-blocking I/O. */
    public static boolean USE_VIRTUAL_THREADS = false;

    public static int MAX_OUTPUT_SIZE = 1024 * 1024;

    public static int MAX_INPUT_SIZE = 1024 * 1024;

    /** Maximum time a thread can wait to obtain a synchronous response in milliseconds */
    public static int MESSAGE_RESPONSE_TIMEOUT = 60_000;

    public static final short CLIENT_BUILD = 1440;

    // MT4 server command codes (received from server in QuoteCmdHandler loop)
    public static final byte CMD_PING = 0x02;
    public static final byte CMD_DISCONNECT = 0x0D;
    public static final byte CMD_QUOTE = (byte) 0x97;
    public static final byte CMD_NEWS = (byte) 0x98;
    public static final byte CMD_MAIL = (byte) 0x99;
    public static final byte CMD_SYMBOL_UPDATE = (byte) 0x9A;
    public static final byte CMD_ORDER_UPDATE = (byte) 0x9B;
    public static final byte CMD_QUOTE_HISTORY = (byte) 0x9C;
    public static final byte CMD_NEWS_UPDATE = (byte) 0x9D;
    public static final byte CMD_CON_GROUP_UPDATE = (byte) 0xAB;
    public static final byte CMD_ORDER_NOTIFY_1 = (byte) 0xBE;
    public static final byte CMD_ORDER_NOTIFY_2 = (byte) 0xD1;

    // MT4 send command codes
    public static final byte SEND_PING = 0x02;
    public static final byte SEND_CHANGE_PASSWORD = 0x04;
    public static final byte SEND_SYMBOLS = 0x08;
    public static final byte SEND_GROUPS = 0x0A;
    public static final byte SEND_DISCONNECT = 0x0D;
    public static final byte SEND_SERVERS_LIST = 0x10;
    public static final byte SEND_SUBSCRIBE = 0x14;
    public static final byte SEND_TERMINAL_ID = 0x18;
    public static final byte SEND_ACCOUNT = 0x1E;
    public static final byte SEND_MAIL_HISTORY = 0x21;
    public static final byte SEND_ORDER_HISTORY = 0x22;
    public static final byte SEND_QUOTE_SUBSCRIBE = (byte) 0x96;
    public static final byte SEND_QUOTE_HISTORY = (byte) 0x9C;
    public static final byte SEND_ORDER_TRANSACTION = (byte) 0xBE;
    public static final byte SEND_ORDER_TRANSACTION_V2 = (byte) 0xD1;

    // Login identification packet types
    public static final short LOGIN_ID_TYPE = 9;
    public static final short LOGIN_ID_DATA_TYPE = 11;
    public static final short LOGIN_ID_EX_TYPE = 15;

    /** Magic XOR constant used in login identification */
    public static final long LOGIN_XOR_CONSTANT = 0x05286AED3286692AL;
    public static final long LOGIN_ID_EX_XOR = 0x4367468243443L;

    /** Client EXE size constant used in terminal identification */
    public static final int CLIENT_EXE_SIZE = 0xD078F9C4;
    public static final int CLIENT_EXE_HASH = 0xCD75F640;
}
