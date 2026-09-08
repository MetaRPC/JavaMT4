package pro.mrpc.mt4.network.parts;

import pro.mrpc.mt4.network.enums.*;

/**
 * Extended symbol configuration data (raw binary structure, 0x790 = 1936 bytes per record).
 * Contains all server-side symbol parameters as received from the MT4 server.
 */
public class SymbolInfoEx {
    // --- common settings ---
    /** Symbol name (12 bytes). */
    public byte[] symbol;
    /** Symbol description. */
    public String description;
    /** Synonym / source. */
    public String source;
    /** Base currency. */
    public String currency;
    /** Security group index (see {@link ConSymbolGroup}). */
    public int type;
    /** Decimal precision. */
    public int digits;
    /** Trade mode. */
    public TradeMode trade;

    // --- external settings ---
    /** Background color. */
    public int backgroundColor;
    /** Symbol index (used as short code). */
    public int count;
    /** Symbol index in market watch. */
    public int countOriginal;
    /** Internal reserved fields. */
    public int[] externalUnused;

    // --- sessions ---
    /** Allow real time quotes. */
    public int realtime;
    /** Trades starting date (UNIX time). */
    public int starting;
    /** Trades end date (UNIX time). */
    public int expiration;
    /** Quote and trade sessions (7 days). */
    public ConSessions[] sessions;

    // --- profits ---
    /** Profit calculation mode. */
    public ProfitMode profitMode;
    /** Reserved. */
    public int profitReserved;

    // --- filtration ---
    /** Filter value. */
    public int filter;
    /** Filtration parameter. */
    public int filterCounter;
    /** Max permissible deviation from last quote (percent). */
    public double filterLimit;
    /** Smoothing. */
    public int filterSmoothing;
    /** Reserved. */
    public int filterReserved;
    /** Enable quote logging. */
    public int logging;

    // --- spread & swaps ---
    /** Spread in pips. */
    public int spread;
    /** Spread balance. */
    public int spreadBalance;
    /** Execution mode. */
    public Execution exemode;
    /** Enable swaps. */
    public int swapEnable;
    /** Swap type. */
    public SwapType swapType;
    /** Swap value for long positions. */
    public double swapLong;
    /** Swap value for short positions. */
    public double swapShort;
    /** Triple rollover day (0=Monday .. 4=Friday). */
    public int swapRollover3days;

    // --- contract ---
    /** Contract size (lot size). */
    public double contractSize;
    /** One tick value. */
    public double tickValue;
    /** One tick size. */
    public double tickSize;
    /** Stops deviation value in points. */
    public int stopsLevel;
    /** GTC mode. */
    public GtcMode gtcPendings;

    // --- margin calculation ---
    /** Margin calculation mode. */
    public MarginMode marginMode;
    /** Initial margin. */
    public double marginInitial;
    /** Margin maintenance. */
    public double marginMaintenance;
    /** Hedged margin. */
    public double marginHedged;
    /** Margin divider. */
    public double marginDivider;

    // --- calculated variables ---
    /** Point size (1 / 10^digits). */
    public double point;
    /** Multiplier (10^digits). */
    public double multiply;
    /** Tick value for bid. */
    public double bidTickvalue;
    /** Tick value for ask. */
    public double askTickvalue;

    // --- misc ---
    /** Allow only BUY positions. */
    public int longOnly;
    /** Max volume for Instant Execution. */
    public int instantMaxVolume;
    /** Currency of margin requirements. */
    public String marginCurrency;
    /** Modification freeze level (from market price). */
    public int freezeLevel;
    /** Lock open checking mode. */
    public int marginHedgedStrong;
    /** Value date for this security. */
    public int valueDate;
    /** Quotes delay. */
    public int quotesDelay;
    /** Use open price at swaps calculation in SWAP_BY_INTEREST mode. */
    public int swapOpenprice;
    /** Reserved. */
    public int[] unused;

    @Override
    public String toString() {
        String name = symbol != null ? new String(symbol).trim() : "";
        return "SymbolInfoEx{" + name + ", digits=" + digits + ", trade=" + trade
                + ", execution=" + exemode + ", spread=" + spread
                + ", contractSize=" + contractSize + ", stopsLevel=" + stopsLevel
                + ", freezeLevel=" + freezeLevel + ", swapType=" + swapType
                + ", swapLong=" + swapLong + ", swapShort=" + swapShort
                + ", tickValue=" + tickValue + ", tickSize=" + tickSize
                + ", profitMode=" + profitMode + ", marginMode=" + marginMode
                + ", gtcPendings=" + gtcPendings + '}';
    }
}
