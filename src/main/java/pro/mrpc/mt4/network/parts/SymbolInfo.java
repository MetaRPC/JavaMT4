package pro.mrpc.mt4.network.parts;

import pro.mrpc.mt4.network.enums.Execution;
import pro.mrpc.mt4.network.enums.MarginMode;
import pro.mrpc.mt4.network.enums.ProfitMode;

/**
 * High-level instrument parameters for a symbol.
 * Created from {@link SymbolInfoEx} during symbol table initialization.
 */
public class SymbolInfo {
    /** Execution type (Request, Instant, Market). */
    public Execution execution;
    /** Stop level in points. */
    public int stopsLevel;
    /** Count of digits after decimal point in symbol prices. */
    public int digits;
    /** Point size in the quote currency (1 / 10^digits). */
    public double point;
    /** Swap of the long position. */
    public double swapLong;
    /** Swap of the short position. */
    public double swapShort;
    /** Spread value in pips. */
    public int spread;
    /** Order freeze level in points. */
    public int freezeLevel;
    /** Currency of margin requirements. */
    public String marginCurrency;
    /** Profit calculation mode. */
    public ProfitMode profitMode;
    /** Margin calculation mode. */
    public MarginMode marginMode;
    /** Contract size (lot size). */
    public double contractSize;
    /** Base currency. */
    public String currency;
    /** Margin divider. */
    public double marginDivider;
    /** Extended symbol data (raw). */
    public SymbolInfoEx ex;
    /** Internal symbol code. */
    public short code;

    @Override
    public String toString() {
        return "SymbolInfo{digits=" + digits + ", stopsLevel=" + stopsLevel
                + ", execution=" + execution + ", spread=" + spread
                + ", contractSize=" + contractSize + '}';
    }
}
