/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/tools/ProtobufInspector.java - INTERACTIVE PROTOBUF INSPECTOR (OFFLINE)
 PURPOSE:
   Explore the mt4_term_api protobuf messages, fields and enums straight from the
   generated descriptors - no terminal connection required. Answers "what is the
   exact field name?", "what type is it?", "which enum value do I pass?" without
   grepping the generated code.

 🎯 WHO SHOULD USE THIS:
   • Developers wiring up requests who need the real protobuf field/enum names
   • Anyone debugging a type mismatch against the API
   • Learning the shape of the MT4 gRPC message set

 📚 WHAT THIS DEMO COVERS:
   Interactive REPL commands:
      • list / ls          - list all message types
      • <TypeName>         - inspect a message (its fields and their types)
      • search <text>      - find types whose name contains text (alias: find)
      • field <name>       - find messages that contain a matching field
      • enum <name>        - show every value of an enum
      • enums              - list all enum types
      • help / ?           - command reference
      • exit / quit / q    - leave the inspector

 ℹ️  SAFE: offline, no connection - reads generated descriptors only, never trades.

 USAGE:
   run.bat inspect      (or: run.bat 9)
══════════════════════════════════════════════════════════════════════════════*/
package examples.tools;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Interactive Protobuf Inspector for the MT4 gRPC API (offline).
 *
 * <p>Explores the {@code mt4_term_api} protobuf messages, fields and enums straight from the
 * generated descriptors — no terminal connection required. Use it to answer "what is the exact
 * field name?", "what type is it?", "which enum value do I pass?" without grepping the gencode.
 *
 * <p><b>Run:</b> {@code run.bat inspect}  (or {@code run.bat 9}).
 *
 * <pre>
 *   COMMAND            DESCRIPTION
 *   ──────────────────────────────────────────────────────────────────
 *   list, ls           List all message types
 *   &lt;TypeName&gt;          Inspect a message — its fields and their types
 *   search &lt;text&gt;       Find types whose name contains text (alias: find)
 *   field &lt;name&gt;        Find messages that contain a field matching name
 *   enum &lt;name&gt;         Show every value of an enum
 *   enums              List all enum types
 *   help, ?            Show the command reference
 *   exit, quit, q      Leave the inspector
 * </pre>
 *
 * <p>Descriptors are read via {@code FileDescriptor.getMessageTypes()/getEnumTypes()}, so the
 * names shown are the real protobuf names — the same ones you look up with {@code getField()}.
 */
public final class ProtobufInspector {

    private ProtobufInspector() {}

    //region INDEXING
    // ══════════════════════════════════════════════════════════════════
    // INDEXING - build the name -> descriptor maps once from the proto files
    // ══════════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────────
    // files()
    //   One generated outer class per proto file; each exposes its file
    //   descriptor, which is where every message/enum is enumerated.
    // ──────────────────────────────────────────────────────────────────
    /** One generated outer class per proto file; each exposes the file descriptor. */
    private static List<FileDescriptor> files() {
        return List.of(
                mt4_term_api.Mt4TermApiAccountHelper.getDescriptor(),
                mt4_term_api.Mt4TermApiAccounts.getDescriptor(),
                mt4_term_api.Mt4TermApiConnection.getDescriptor(),
                mt4_term_api.Mt4TermApiDemoAccount.getDescriptor(),
                mt4_term_api.Mt4TermApiGui.getDescriptor(),
                mt4_term_api.Mt4TermApiHealthCheck.getDescriptor(),
                mt4_term_api.Mt4TermApiInternalCharts.getDescriptor(),
                mt4_term_api.Mt4TermApiMarketInfo.getDescriptor(),
                mt4_term_api.Mt4TermApiPublicAccount.getDescriptor(),
                mt4_term_api.Mt4TermApiQuoteHistory.getDescriptor(),
                mt4_term_api.Mt4TermApiServerTimezone.getDescriptor(),
                mt4_term_api.Mt4TermApiSubscriptions.getDescriptor(),
                mt4_term_api.Mt4TermApiTradeStats.getDescriptor(),
                mt4_term_api.Mt4TermApiTradingHelper.getDescriptor(),
                mt4_term_api.MrpcMt4Error.getDescriptor());
    }

    // Built once: message name -> descriptor, enum name -> descriptor (nested types included).
    private static final Map<String, Descriptor> MESSAGES = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final Map<String, EnumDescriptor> ENUMS = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private static void index() {
        if (!MESSAGES.isEmpty()) return;
        for (FileDescriptor fd : files()) {
            for (Descriptor m : fd.getMessageTypes()) indexMessage(m);
            for (EnumDescriptor e : fd.getEnumTypes()) ENUMS.putIfAbsent(e.getName(), e);
        }
    }

    private static void indexMessage(Descriptor m) {
        MESSAGES.putIfAbsent(m.getName(), m);
        for (EnumDescriptor e : m.getEnumTypes()) ENUMS.putIfAbsent(e.getName(), e);
        for (Descriptor n : m.getNestedTypes()) {
            // Skip the synthetic map entry helper types protobuf generates for map<> fields.
            if (n.getOptions().getMapEntry()) continue;
            indexMessage(n);
        }
    }

    //endregion

    //region ENTRY POINT (REPL)
    // ══════════════════════════════════════════════════════════════════
    // ENTRY POINT - the read-eval-print loop that dispatches commands
    // ══════════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────────
    // run()
    //   Index the types, print the banner, then loop: read a line, parse
    //   the command + argument, and dispatch to the matching handler.
    // ──────────────────────────────────────────────────────────────────
    public static void run() {
        index();
        printHeader();
        printQuickStart();

        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("\n> ");
                if (!in.hasNextLine()) return;
                String input = in.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split("\\s+", 2);
                String command = parts[0].toLowerCase(Locale.ROOT);
                String arg = parts.length > 1 ? parts[1].trim() : "";

                switch (command) {
                    case "exit":
                    case "quit":
                    case "q":
                        System.out.println("\n👋 Goodbye!");
                        return;
                    case "help":
                    case "?":
                        printHelp();
                        break;
                    case "list":
                    case "ls":
                        listTypes();
                        break;
                    case "enums":
                        listEnums();
                        break;
                    case "search":
                    case "find":
                        if (arg.isEmpty()) System.out.println("❌ Usage: search <text>");
                        else searchTypes(arg);
                        break;
                    case "field":
                        if (arg.isEmpty()) System.out.println("❌ Usage: field <fieldname>");
                        else findField(arg);
                        break;
                    case "enum":
                        if (arg.isEmpty()) System.out.println("❌ Usage: enum <enumname>");
                        else inspectEnum(arg);
                        break;
                    default:
                        inspectType(input); // assume a type name
                }
            }
        }
    }

    //endregion

    //region COMMANDS
    // ══════════════════════════════════════════════════════════════════
    // COMMANDS - one handler per REPL command
    // ══════════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────────
    // listTypes() / listEnums()
    //   Print every indexed message type (tagged Data/Request/Reply) or
    //   every enum type with its value count.
    // ──────────────────────────────────────────────────────────────────
    private static void listTypes() {
        System.out.println("\n📦 " + MESSAGES.size() + " message types:\n");
        for (Descriptor m : sortedMessages(MESSAGES.values())) {
            String tag = m.getName().endsWith("Data") ? "[Data]   "
                    : m.getName().endsWith("Request") ? "[Request]"
                    : m.getName().endsWith("Reply") ? "[Reply]  " : "[Message]";
            System.out.println("  " + tag + " " + m.getName());
        }
        System.out.println("\n💡 Type a name to inspect it (e.g. 'AccountSummaryData'). 'enums' lists enums.");
    }

    private static void listEnums() {
        System.out.println("\n🔢 " + ENUMS.size() + " enum types:\n");
        for (EnumDescriptor e : sortedEnums(ENUMS.values())) {
            System.out.println("  [Enum] " + e.getName() + "  (" + e.getValues().size() + " values)");
        }
        System.out.println("\n💡 'enum <Name>' shows every value.");
    }

    // ──────────────────────────────────────────────────────────────────
    // searchTypes(term)
    //   Find every message/enum whose name contains the search text.
    // ──────────────────────────────────────────────────────────────────
    private static void searchTypes(String term) {
        System.out.println("\n🔍 Types containing '" + term + "':\n");
        int found = 0;
        for (Descriptor m : sortedMessages(MESSAGES.values())) {
            if (containsIgnoreCase(m.getName(), term)) {
                System.out.println("  [Message] " + m.getName());
                found++;
            }
        }
        for (EnumDescriptor e : sortedEnums(ENUMS.values())) {
            if (containsIgnoreCase(e.getName(), term)) {
                System.out.println("  [Enum]    " + e.getName());
                found++;
            }
        }
        if (found == 0) System.out.println("  ❌ Nothing matched '" + term + "'");
        else System.out.println("\n✅ " + found + " match(es). Type a name to inspect it.");
    }

    // ──────────────────────────────────────────────────────────────────
    // findField(fieldName)
    //   Find every message that declares a field matching the name, and
    //   print that field's proto type.
    // ──────────────────────────────────────────────────────────────────
    private static void findField(String fieldName) {
        System.out.println("\n🔍 Messages with a field matching '" + fieldName + "':\n");
        int found = 0;
        for (Descriptor m : sortedMessages(MESSAGES.values())) {
            List<FieldDescriptor> hits = new ArrayList<>();
            for (FieldDescriptor f : m.getFields()) {
                if (containsIgnoreCase(f.getName(), fieldName)) hits.add(f);
            }
            if (!hits.isEmpty()) {
                System.out.println("📦 " + m.getName() + ":");
                for (FieldDescriptor f : hits) {
                    System.out.println("   └─ " + f.getName() + " : " + typeName(f));
                }
                found++;
            }
        }
        if (found == 0) System.out.println("  ❌ No message has a field containing '" + fieldName + "'");
        else System.out.println("\n✅ Found in " + found + " type(s).");
    }

    // ──────────────────────────────────────────────────────────────────
    // inspectEnum(name)
    //   Show every value of an enum, sorted by its numeric tag.
    // ──────────────────────────────────────────────────────────────────
    private static void inspectEnum(String name) {
        EnumDescriptor e = ENUMS.get(name);
        if (e == null) {
            System.out.println("\n❌ Enum '" + name + "' not found.  💡 Try: enum EnumAccountTradeMode");
            return;
        }
        System.out.println("\n╔═══ ENUM: " + e.getName() + " ═══╗\n");
        List<EnumValueDescriptor> values = new ArrayList<>(e.getValues());
        values.sort(Comparator.comparingInt(EnumValueDescriptor::getNumber));
        for (EnumValueDescriptor v : values) {
            System.out.printf("  %-48s = %d%n", v.getName(), v.getNumber());
        }
    }

    // ──────────────────────────────────────────────────────────────────
    // inspectType(name)
    //   Show a message's fields with their proto number and type (falls
    //   back to inspectEnum if the name is actually an enum).
    // ──────────────────────────────────────────────────────────────────
    private static void inspectType(String name) {
        Descriptor m = MESSAGES.get(name);
        if (m == null) {
            if (ENUMS.containsKey(name)) { inspectEnum(name); return; }
            System.out.println("\n❌ Type '" + name + "' not found.");
            System.out.println("💡 Try 'search " + name + "' or 'list'.");
            return;
        }
        System.out.println("\n╔═══ MESSAGE: " + m.getName() + " ═══╗\n");
        List<FieldDescriptor> fields = new ArrayList<>(m.getFields());
        if (fields.isEmpty()) {
            System.out.println("  (no fields)");
            return;
        }
        System.out.println("📋 Fields (" + fields.size() + "):\n");
        fields.sort(Comparator.comparingInt(FieldDescriptor::getNumber));
        for (FieldDescriptor f : fields) {
            String prefix = f.isRepeated() ? "  📚" : "  •";
            System.out.printf("%s #%-3d %-40s : %s%n", prefix, f.getNumber(), f.getName(), typeName(f));
        }
        System.out.println("\n💡 For an enum field, run: enum <EnumName>");
    }

    //endregion

    //region HELPERS
    // ══════════════════════════════════════════════════════════════════
    // HELPERS - type formatting, case-insensitive search, stable sorting
    // ══════════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────────
    // typeName(f) / scalarOrName(f)
    //   Render a field's proto type: scalar name, message/enum name, with
    //   repeated/map<> decoration.
    // ──────────────────────────────────────────────────────────────────
    /** A readable proto type for a field: scalar name, message/enum name, repeated/map decorated. */
    private static String typeName(FieldDescriptor f) {
        if (f.isMapField()) {
            Descriptor entry = f.getMessageType();
            FieldDescriptor k = entry.findFieldByName("key");
            FieldDescriptor v = entry.findFieldByName("value");
            return "map<" + scalarOrName(k) + ", " + scalarOrName(v) + ">";
        }
        String base = scalarOrName(f);
        return f.isRepeated() ? "repeated " + base : base;
    }

    private static String scalarOrName(FieldDescriptor f) {
        switch (f.getType()) {
            case MESSAGE:
            case GROUP:
                return f.getMessageType().getName();
            case ENUM:
                return f.getEnumType().getName();
            default:
                return f.getType().name().toLowerCase(Locale.ROOT); // int64, double, string, bool, ...
        }
    }

    private static boolean containsIgnoreCase(String haystack, String needle) {
        return haystack.toLowerCase(Locale.ROOT).contains(needle.toLowerCase(Locale.ROOT));
    }

    private static List<Descriptor> sortedMessages(Collection<Descriptor> in) {
        List<Descriptor> out = new ArrayList<>(in);
        out.sort(Comparator.comparing(Descriptor::getName));
        return out;
    }

    private static List<EnumDescriptor> sortedEnums(Collection<EnumDescriptor> in) {
        List<EnumDescriptor> out = new ArrayList<>(in);
        out.sort(Comparator.comparing(EnumDescriptor::getName));
        return out;
    }

    //endregion

    //region UI TEXT
    // ══════════════════════════════════════════════════════════════════
    // UI TEXT - the banner, quick-start box and full command reference
    // ══════════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────────
    // printHeader() / printQuickStart() / printHelp()
    //   Static console output: the title banner, the quick-start hints and
    //   the full command reference.
    // ──────────────────────────────────────────────────────────────────
    private static void printHeader() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║        🔍 PROTOBUF INSPECTOR — mt4_term_api           ║");
        System.out.println("║   Explore MT4 gRPC messages, fields & enums offline   ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("Indexed " + MESSAGES.size() + " messages, " + ENUMS.size() + " enums.");
    }

    private static void printQuickStart() {
        System.out.println("\n┌─ QUICK START ─────────────────────────────────────────┐");
        System.out.println("│ list                list every message type           │");
        System.out.println("│ AccountSummaryData  inspect a message's fields        │");
        System.out.println("│ field Balance       find who declares a field         │");
        System.out.println("│ enum EnumAccountTradeMode   show enum values          │");
        System.out.println("│ help                full command reference            │");
        System.out.println("│ exit                quit                              │");
        System.out.println("└───────────────────────────────────────────────────────┘");
    }

    private static void printHelp() {
        System.out.println("\n═══ COMMAND REFERENCE ═══\n");
        System.out.println("BROWSE");
        System.out.println("  list, ls             list all message types");
        System.out.println("  enums                list all enum types");
        System.out.println("  <TypeName>           inspect a message (fields + types)");
        System.out.println("\nSEARCH");
        System.out.println("  search <text>        find types by name (alias: find)");
        System.out.println("  field <name>         find messages containing a field");
        System.out.println("  enum <name>          show enum values");
        System.out.println("\nUTILITY");
        System.out.println("  help, ?              this help");
        System.out.println("  exit, quit, q        leave");
        System.out.println("\nEXAMPLES");
        System.out.println("  > list");
        System.out.println("  > OpenedOrdersData");
        System.out.println("  > search Order");
        System.out.println("  > field Ticket");
        System.out.println("  > enum OrderSendOperationType");
    }
    //endregion
}
