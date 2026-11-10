# Protobuf Inspector — Interactive Type Explorer

> An offline developer tool that explores the MT4 gRPC protobuf **messages, fields and enums** — browse, search, find which message declares a field, and list enum values, all **without connecting to MT4**.

It reads the generated protobuf **descriptors** (`FileDescriptor.getMessageTypes()` / `getEnumTypes()`) at startup, so the names it shows are the exact ones your code looks up — the same field names you pass to `getField(...)` and the builders.

---

## 🏁 Running it

The inspector is an offline example — it does **not** connect to a terminal:

```bat
run.bat 9
```

(`run.bat inspect` and `run.bat protobuf` are aliases.)

You'll see:

```
╔══════════════════════════════════════════════════════╗
║        🔍 PROTOBUF INSPECTOR — mt4_term_api         ║
║   Explore MT4 gRPC messages, fields & enums offline  ║
╚══════════════════════════════════════════════════════╝
Indexed 196 messages, 23 enums.

┌─ QUICK START ─────────────────────────────────────────┐
│ list                list every message type           │
│ AccountSummaryData  inspect a message's fields        │
│ field Balance       find who declares a field         │
│ enum EnumAccountTradeMode   show enum values          │
│ help                full command reference            │
│ exit                quit                              │
└───────────────────────────────────────────────────────┘

>
```

---

## 🧰 Commands

| Command | Description | Example |
|---------|-------------|---------|
| `list` / `ls` | List all message types | `list` |
| `enums` | List all enum types | `enums` |
| `<TypeName>` | Inspect a message — its fields and types | `AccountSummaryData` |
| `search <text>` / `find <text>` | Search types by name | `search Order` |
| `field <name>` | Find messages that contain a field | `field ticket` |
| `enum <name>` | Show an enum's values | `enum OrderSendOperationType` |
| `help` / `?` | Help | `help` |
| `exit` / `quit` / `q` | Exit | `exit` |

Type names and field names are **case-insensitive**.

---

## Practical examples

### 1) Inspect a message's fields

```
> AccountSummaryData
```
```
╔═══ MESSAGE: AccountSummaryData ═══╗

📋 Fields (20):

  • #1   account_login                            : int64
  • #2   account_balance                          : double
  • #3   account_equity                           : double
  • #6   account_trade_mode                       : EnumAccountTradeMode
  • #9   server_time                              : Timestamp
  ...
```

The `#n` is the protobuf field tag; the type is the proto type (`repeated …` and `map<…>` are shown where relevant, and message/enum fields show the referenced type name). These are the real field names behind the generated getters, e.g. `getAccountBalance()`.

### 2) Which messages have a `ticket` field?

```
> field ticket
```
```
📦 HistoryOrderInfo:
   └─ ticket : int32
📦 OpenedOrderInfo:
   └─ ticket : int32
📦 OnOpenedOrdersTicketsData:
   └─ position_tickets : repeated int32
   └─ pending_order_tickets : repeated int32
```

### 3) See an enum's values

```
> enum OrderSendOperationType
```
```
╔═══ ENUM: OrderSendOperationType ═══╗

  OC_OP_BUY                                        = 0
  OC_OP_SELL                                       = 1
  OC_OP_BUYLIMIT                                   = 2
  OC_OP_SELLLIMIT                                  = 3
  OC_OP_BUYSTOP                                    = 4
  OC_OP_SELLSTOP                                   = 5
```

### 4) Find related types

```
> search Order
```
```
  [Message] OrderSendData
  [Message] OrderModifyData
  [Message] OpenedOrdersData
  ...
```

---

## 🧩 How it works

- Each generated outer class (`mt4_term_api.Mt4TermApiAccountHelper`, `…TradingHelper`, …) exposes a `FileDescriptor` via `getDescriptor()`.
- At startup the inspector collects all of them and walks `getMessageTypes()` and `getEnumTypes()` (recursing into nested types, skipping synthetic `map<>` entry helpers) into two indexes.
- Everything is **static descriptor reflection** — no terminal connection, no network.

Source: [`src/main/java/examples/tools/ProtobufInspector.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/examples/tools/ProtobufInspector.java).

---

**See also:** [ENUMs Usage Reference](ENUMS_USAGE_REFERENCE.md) · [Project Map](PROJECT_MAP.md) · [Glossary](GLOSSARY.md)
