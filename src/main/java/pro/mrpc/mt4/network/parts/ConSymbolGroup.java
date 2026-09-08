package pro.mrpc.mt4.network.parts;

/**
 * Symbol group definition (80 bytes in MT4 protocol).
 * There are always 32 symbol groups on an MT4 server.
 */
public class ConSymbolGroup {
    /** Group name (max 16 characters). */
    public String name;
    /** Group description (max 64 characters). */
    public String description;

    @Override
    public String toString() {
        return "ConSymbolGroup{name='" + name + "', description='" + description + "'}";
    }
}
