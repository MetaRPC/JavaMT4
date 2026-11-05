# Server Timezone

> The trade server's timezone / UTC offset — align timestamps and sessions to broker time.

**API Information:**

* **Low-level API:** `MT4Account.serverTimezone()` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.ServerTimezone`
* **RPC:** `ServerTimezone(ServerTimezoneRequest) -> ServerTimezoneReply`
* **Payload:** `ServerTimezoneData`

## 💬 Just the essentials

* **What it is.** The broker server's UTC offset (and a label/source for it).
* **Why you need it.** Server timestamps are in broker time; this lets you convert to/from UTC and reason about trading sessions.
* **When to use.** When displaying times, scheduling around sessions, or comparing server time to UTC.

---

## 🎯 Purpose

* Server UTC offset (seconds)
* Server/timezone label
* Source and validation info for the offset

---

## Method Signature

```java
public ServerTimezoneData serverTimezone()
```

No arguments.

**Request message:**

```protobuf
message ServerTimezoneRequest {
}
```

**Payload message:**

```protobuf
message ServerTimezoneData {
  string server = 1;                  // server / timezone label
  int32 utc_offset_seconds = 2;       // offset from UTC, in seconds
  SERVER_TIMEZONE_SOURCE source = 3;  // enum: how the offset was determined
  Timestamp validated_utc = 4;        // when the offset was last validated (UTC)
}
```

---

## 🔽 Input

None.

---

## ⬆️ Output — `ServerTimezoneData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `server` | `getServer()` | Server / timezone label |
| `utc_offset_seconds` | `getUtcOffsetSeconds()` | Offset from UTC, in seconds (e.g. 3600 = UTC+1) |
| `source` | `getSource()` | How the offset was determined (`SERVER_TIMEZONE_SOURCE`, see below) |
| `validated_utc` | `getValidatedUtc()` | When the offset was last validated (protobuf `Timestamp`) |

---

## 🧱 Related enums (from proto)

### `SERVER_TIMEZONE_SOURCE` — field `source`

| Constant | Value | Meaning |
|----------|-------|---------|
| `SERVER_TIMEZONE_SOURCE_UNKNOWN` | 0 | Source not known |
| `SERVER_TIMEZONE_SOURCE_LIVE` | 1 | Offset read live from the server |
| `SERVER_TIMEZONE_SOURCE_LAST_VALIDATED` | 2 | Last validated offset (cached) |

> Access via `Mt4TermApiServerTimezone.SERVER_TIMEZONE_SOURCE.SERVER_TIMEZONE_SOURCE_LIVE`.

---

## 🧩 Notes & Tips

* **Offset is in seconds.** Divide by 3600 for hours (e.g. `3600 → UTC+1`, `10800 → UTC+3`).
* **Server time vs UTC.** `accountSummary()` returns both `serverTime` and `serverTimeUtc`; this call gives you the offset to convert any server timestamp yourself.
* **DST.** Many FX servers shift with daylight saving — read this at session start rather than hard-coding an offset.
* **Rarely changes within a session** — fetch once and cache; refresh daily or on reconnect.

---

## 🔗 Usage Examples

### 1) Print the server offset

```java
static ServerTimezoneData showTimezone(MT4Account account) {
    var tz = account.serverTimezone();
    double hours = tz.getUtcOffsetSeconds() / 3600.0;
    System.out.printf(Locale.ROOT, "%s: UTC%+.0f (%ds)  source=%s%n",
            tz.getServer(), hours, tz.getUtcOffsetSeconds(), tz.getSource());
    return tz;
}
```

### 2) Convert server time ↔ UTC

Use the offset to turn a server timestamp into UTC (and back).

```java
static Instant serverToUtc(MT4Account account, Instant serverDt) {
    return serverDt.minusSeconds(account.serverTimezone().getUtcOffsetSeconds());
}

static Instant utcToServer(MT4Account account, Instant utcDt) {
    return utcDt.plusSeconds(account.serverTimezone().getUtcOffsetSeconds());
}
```

### 3) Is it the weekend (server time)?

Gate trading around the broker's week using server-local time.

```java
static boolean isWeekendServer(MT4Account account) {
    long off = account.serverTimezone().getUtcOffsetSeconds();
    var serverNow = Instant.now().plusSeconds(off).atZone(ZoneOffset.UTC);
    var dow = serverNow.getDayOfWeek();
    return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
}
```

---

## ⚠️ Pitfalls

* **Seconds, not hours.** `utc_offset_seconds` is seconds — convert before formatting.
* **DST drift.** Don't cache the offset across days; brokers shift with daylight saving.
* **Session math.** Trading sessions and rollover are defined in server time — convert before comparing to UTC schedules.

---

## 🧪 Testing Suggestions

* **Happy path.** `utc_offset_seconds` is a plausible value (typically a multiple of 1800) and `server` is non-empty.
* **Consistency.** `accountSummary().getServerTime()` minus `getServerTimeUtc()` roughly equals `utc_offset_seconds`.
* **Conversion round-trip.** `utcToServer(serverToUtc(dt))` returns the original instant.

---

## 📚 See also

* [Account Summary](../1.%20Account_Information/account_summary.md)
* [Quote History (OHLC)](quote_history.md)
* [Symbols](symbols.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
