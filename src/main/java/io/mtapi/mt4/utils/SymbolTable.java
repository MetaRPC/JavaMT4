package io.mtapi.mt4.utils;

import io.mtapi.mt4.network.enums.*;
import io.mtapi.mt4.network.parts.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all symbol data received from the MT4 server.
 * Parses raw binary buffers into {@link SymbolInfo} / {@link SymbolInfoEx} objects
 * and {@link ConSymbolGroup} symbol groups.
 *
 * <p>Each symbol record is 0x790 (1936) bytes.
 * Each symbol group record is 80 bytes (always 32 groups).
 */
public class SymbolTable {
    private static final int RECORD_SIZE = 0x790;         // 1936 bytes per symbol
    private static final int UPDATE_RECORD_SIZE = 0x7A0;  // 1952 bytes per symbol update
    private static final int GROUP_RECORD_SIZE = 80;      // 80 bytes per ConSymbolGroup
    private static final int GROUP_COUNT = 32;             // always 32 symbol groups

    private final Map<Short, String> codeToName = new HashMap<>();
    private final Map<String, SymbolInfo> nameToInfo = new HashMap<>();
    private String[] names = new String[0];
    private ConSymbolGroup[] symbolGroups;

    // ======================== Symbol Parsing ========================

    /**
     * Parses the raw symbols buffer received from the server.
     */
    public void init(byte[] buf) {
        if (buf == null) throw new IllegalArgumentException("Symbols buffer is null");

        codeToName.clear();
        nameToInfo.clear();

        int count = buf.length / RECORD_SIZE;
        for (int i = 0; i < count; i++) {
            SymbolInfoEx ex = parseSymbolInfoEx(buf, i * RECORD_SIZE);
            SymbolInfo info = createSymbolInfo(ex);
            String name = BitConverter.getString(ex.symbol, 0, 12);
            codeToName.put(info.code, name);
            nameToInfo.put(name, info);
        }
        names = codeToName.values().toArray(new String[0]);
    }

    /**
     * Updates existing symbols from a server symbol update buffer.
     * Each update record is 0x7A0 bytes with a 16-byte header.
     */
    public void update(byte[] buf) {
        int count = buf.length / UPDATE_RECORD_SIZE;
        for (int i = 0; i < count; i++) {
            int base = i * UPDATE_RECORD_SIZE;
            if (BitConverter.toInt32(buf, base + 4) != 2) {
                continue; // only process action code 2 (update)
            }
            SymbolInfoEx ex = parseSymbolInfoEx(buf, base + 16);
            String name = BitConverter.getString(ex.symbol, 0, 12);
            if (nameToInfo.containsKey(name)) {
                nameToInfo.put(name, createSymbolInfo(ex));
            }
        }
    }

    // ======================== Group Parsing ========================

    /**
     * Parses the raw groups buffer received from the server.
     * Always contains 32 ConSymbolGroup records of 80 bytes each.
     */
    public void initGroups(byte[] buf) {
        if (buf == null) throw new IllegalArgumentException("Groups buffer is null");

        symbolGroups = new ConSymbolGroup[GROUP_COUNT];
        for (int i = 0; i < GROUP_COUNT; i++) {
            int offset = i * GROUP_RECORD_SIZE;
            ConSymbolGroup group = new ConSymbolGroup();
            group.name = BitConverter.getString(buf, offset, 16);
            group.description = BitConverter.getString(buf, offset + 16, 64);
            symbolGroups[i] = group;
        }
    }

    // ======================== SymbolInfoEx Binary Deserialization ========================

    /**
     * Parses a single SymbolInfoEx record from a raw buffer at the given offset.
     * Layout matches the original MT4 UDT.ReadStruct for SymbolInfoEx.
     */
    private static SymbolInfoEx parseSymbolInfoEx(byte[] buf, int of) {
        SymbolInfoEx s = new SymbolInfoEx();

        // --- common settings (offset 0-163) ---
        s.symbol = new byte[12];
        System.arraycopy(buf, of, s.symbol, 0, 12);
        s.description = BitConverter.getString(buf, of + 12, 64);
        s.source = BitConverter.getString(buf, of + 76, 12);
        s.currency = BitConverter.getString(buf, of + 88, 12);
        s.type = BitConverter.toInt32(buf, of + 100);
        s.digits = BitConverter.toInt32(buf, of + 104);
        s.trade = TradeMode.forValue(BitConverter.toInt32(buf, of + 108));
        s.backgroundColor = BitConverter.toInt32(buf, of + 112);
        s.count = BitConverter.toInt32(buf, of + 116);
        s.countOriginal = BitConverter.toInt32(buf, of + 120);
        s.externalUnused = new int[7];
        for (int o = 0; o < 7; o++)
            s.externalUnused[o] = BitConverter.toInt32(buf, of + 124 + o * 4);
        s.realtime = BitConverter.toInt32(buf, of + 152);
        s.starting = BitConverter.toInt32(buf, of + 156);
        s.expiration = BitConverter.toInt32(buf, of + 160);

        // --- sessions (offset 164, 7 × 208 bytes) ---
        s.sessions = new ConSessions[7];
        for (int p = 0; p < 7; p++) {
            ConSessions cs = new ConSessions();
            cs.quote = new ConSession[3];
            for (int q = 0; q < 3; q++) {
                ConSession ses = new ConSession();
                ses.openHour = BitConverter.toInt16(buf, of + p * 208 + q * 32 + 164);
                ses.openMin = BitConverter.toInt16(buf, of + p * 208 + q * 32 + 166);
                ses.closeHour = BitConverter.toInt16(buf, of + p * 208 + q * 32 + 168);
                ses.closeMin = BitConverter.toInt16(buf, of + p * 208 + q * 32 + 170);
                ses.open = BitConverter.toInt32(buf, of + p * 208 + q * 32 + 172);
                ses.close = BitConverter.toInt32(buf, of + p * 208 + q * 32 + 176);
                ses.align = new short[8];
                for (int r = 0; r < 8; r++)
                    ses.align[r] = BitConverter.toInt16(buf, of + p * 208 + q * 32 + 180 + r * 2);
                cs.quote[q] = ses;
            }
            cs.trade = new ConSession[3];
            for (int t = 0; t < 3; t++) {
                ConSession ses = new ConSession();
                ses.openHour = BitConverter.toInt16(buf, of + p * 208 + t * 32 + 260);
                ses.openMin = BitConverter.toInt16(buf, of + p * 208 + t * 32 + 262);
                ses.closeHour = BitConverter.toInt16(buf, of + p * 208 + t * 32 + 264);
                ses.closeMin = BitConverter.toInt16(buf, of + p * 208 + t * 32 + 266);
                ses.open = BitConverter.toInt32(buf, of + p * 208 + t * 32 + 268);
                ses.close = BitConverter.toInt32(buf, of + p * 208 + t * 32 + 272);
                ses.align = new short[8];
                for (int r = 0; r < 8; r++)
                    ses.align[r] = BitConverter.toInt16(buf, of + p * 208 + t * 32 + 276 + r * 2);
                cs.trade[t] = ses;
            }
            cs.quoteOvernight = BitConverter.toInt32(buf, of + p * 208 + 356);
            cs.tradeOvernight = BitConverter.toInt32(buf, of + p * 208 + 360);
            cs.reserved = new int[2];
            for (int u = 0; u < 2; u++)
                cs.reserved[u] = BitConverter.toInt32(buf, of + p * 208 + 364 + u * 4);
            s.sessions[p] = cs;
        }

        // --- profits & filtration (offset 1620+) ---
        s.profitMode = ProfitMode.forValue(BitConverter.toInt32(buf, of + 1620));
        s.profitReserved = BitConverter.toInt32(buf, of + 1624);
        s.filter = BitConverter.toInt32(buf, of + 1628);
        s.filterCounter = BitConverter.toInt32(buf, of + 1632);
        s.filterLimit = BitConverter.toDouble(buf, of + 1636 + 4);
        s.filterSmoothing = BitConverter.toInt32(buf, of + 1644 + 4);
        s.filterReserved = BitConverter.toInt32(buf, of + 1648 + 4);
        s.logging = BitConverter.toInt32(buf, of + 1652 + 4);

        // --- spread & swaps ---
        s.spread = BitConverter.toInt32(buf, of + 1656 + 4);
        s.spreadBalance = BitConverter.toInt32(buf, of + 1660 + 4);
        s.exemode = Execution.forValue(BitConverter.toInt32(buf, of + 1664 + 4));
        s.swapEnable = BitConverter.toInt32(buf, of + 1668 + 4);
        s.swapType = SwapType.forValue(BitConverter.toInt32(buf, of + 1672 + 4));
        s.swapLong = BitConverter.toDouble(buf, of + 1676 + 4);
        s.swapShort = BitConverter.toDouble(buf, of + 1684 + 4);
        s.swapRollover3days = BitConverter.toInt32(buf, of + 1692 + 4);

        // --- contract ---
        s.contractSize = BitConverter.toDouble(buf, of + 1696 + 4 + 4);
        s.tickValue = BitConverter.toDouble(buf, of + 1704 + 4 + 4);
        s.tickSize = BitConverter.toDouble(buf, of + 1712 + 4 + 4);
        s.stopsLevel = BitConverter.toInt32(buf, of + 1720 + 4 + 4);
        s.gtcPendings = GtcMode.forValue(BitConverter.toInt32(buf, of + 1724 + 4 + 4));

        // --- margin ---
        s.marginMode = MarginMode.forValue(BitConverter.toInt32(buf, of + 1728 + 4 + 4));
        s.marginInitial = BitConverter.toDouble(buf, of + 1732 + 4 + 4 + 4);
        s.marginMaintenance = BitConverter.toDouble(buf, of + 1740 + 4 + 4 + 4);
        s.marginHedged = BitConverter.toDouble(buf, of + 1748 + 4 + 4 + 4);
        s.marginDivider = BitConverter.toDouble(buf, of + 1756 + 4 + 4 + 4);

        // --- calculated ---
        s.point = BitConverter.toDouble(buf, of + 1764 + 4 + 4 + 4);
        s.multiply = BitConverter.toDouble(buf, of + 1772 + 4 + 4 + 4);
        s.bidTickvalue = BitConverter.toDouble(buf, of + 1780 + 4 + 4 + 4);
        s.askTickvalue = BitConverter.toDouble(buf, of + 1788 + 4 + 4 + 4);

        // --- misc ---
        s.longOnly = BitConverter.toInt32(buf, of + 1796 + 4 + 4);
        s.instantMaxVolume = BitConverter.toInt32(buf, of + 1800 + 4 + 4 + 4);
        s.marginCurrency = BitConverter.getString(buf, of + 1804 + 4 + 4 + 4, 12);
        s.freezeLevel = BitConverter.toInt32(buf, of + 1816 + 4 + 4 + 4);
        s.marginHedgedStrong = BitConverter.toInt32(buf, of + 1820 + 4 + 4 + 4);
        s.valueDate = BitConverter.toInt32(buf, of + 1824 + 4 + 4 + 4);
        s.quotesDelay = BitConverter.toInt32(buf, of + 1828 + 4 + 4 + 4);
        s.swapOpenprice = BitConverter.toInt32(buf, of + 1832 + 4 + 4 + 4);
        s.unused = new int[22];
        for (int w = 0; w < 22; w++)
            s.unused[w] = BitConverter.toInt32(buf, of + 1836 + w * 4 + 4 + 4 + 4);

        return s;
    }

    // ======================== SymbolInfo Creation ========================

    /**
     * Creates a high-level {@link SymbolInfo} from the raw {@link SymbolInfoEx} data.
     */
    private static SymbolInfo createSymbolInfo(SymbolInfoEx ex) {
        SymbolInfo info = new SymbolInfo();
        info.ex = ex;
        info.code = (short) ex.count;
        info.digits = ex.digits;
        info.point = Math.pow(10, -info.digits);
        info.stopsLevel = ex.stopsLevel;
        info.swapLong = ex.swapLong;
        info.swapShort = ex.swapShort;
        info.spread = ex.spread;
        info.marginCurrency = ex.marginCurrency;
        info.freezeLevel = ex.freezeLevel;
        info.contractSize = ex.contractSize;
        info.currency = ex.currency;
        info.marginDivider = ex.marginDivider;
        info.execution = ex.exemode;
        info.profitMode = ex.profitMode;
        info.marginMode = ex.marginMode;
        return info;
    }

    // ======================== Lookup Methods ========================

    /**
     * Returns the symbol name for a given short code.
     */
    public String getSymbol(short code) {
        return codeToName.get(code);
    }

    /**
     * Returns the short code for a symbol name.
     */
    public short getCode(String symbol) {
        SymbolInfo info = nameToInfo.get(symbol);
        if (info == null) throw new IllegalArgumentException("Symbol not found: " + symbol);
        return info.code;
    }

    /**
     * Returns the full {@link SymbolInfo} for a symbol.
     *
     * @throws IllegalArgumentException if the symbol is not found
     */
    public SymbolInfo getInfo(String symbol) {
        SymbolInfo info = nameToInfo.get(symbol);
        if (info == null) throw new IllegalArgumentException("Symbol not found: " + symbol);
        return info;
    }

    /**
     * Returns the full {@link SymbolInfo} for a symbol, or null if not found.
     */
    public SymbolInfo getInfoOrNull(String symbol) {
        return nameToInfo.get(symbol);
    }

    /**
     * Returns the number of decimal digits for a symbol.
     */
    public int getDigits(String symbol) {
        SymbolInfo info = nameToInfo.get(symbol);
        return info != null ? info.digits : 5;
    }

    /**
     * Returns the execution mode for a symbol.
     */
    public Execution getExecution(String symbol) {
        SymbolInfo info = nameToInfo.get(symbol);
        return info != null ? info.execution : Execution.Instant;
    }

    /**
     * Returns {@code true} if the symbol uses market execution (price and slippage must be 0).
     */
    public boolean isMarketExecution(String symbol) {
        return getExecution(symbol) == Execution.Market;
    }

    /**
     * Returns {@code true} if the symbol exists in the table.
     */
    public boolean exists(String symbol) {
        return nameToInfo.containsKey(symbol);
    }

    /**
     * Returns all symbol names.
     */
    public String[] getNames() {
        return names;
    }

    /**
     * Returns the symbol groups array (always 32 entries), or null if not yet loaded.
     */
    public ConSymbolGroup[] getSymbolGroups() {
        return symbolGroups;
    }

    /**
     * Returns the symbol group for a given symbol, using its {@code type} field as index.
     */
    public ConSymbolGroup getSymbolGroup(String symbol) {
        SymbolInfo info = nameToInfo.get(symbol);
        if (info == null || symbolGroups == null) return null;
        int idx = info.ex.type;
        if (idx < 0 || idx >= symbolGroups.length) return null;
        return symbolGroups[idx];
    }

    public boolean isEmpty() {
        return codeToName.isEmpty();
    }

    public int size() {
        return codeToName.size();
    }
}
