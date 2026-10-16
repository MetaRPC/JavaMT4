# API methods

Every public method of the MT4 API, generated from the protos this package is built from. Each is a gRPC method of
package `mt4_term_api` on `mt4.mrpc.pro:443` and a REST route on `https://mt4.mrpc.pro` (server streams also as a WebSocket on the same
path, `wss://mt4.mrpc.pro/...?id=...&apikey=...`). Every reply carries either `data` (described below) or `error`
(see [Errors](../All_Guides/ERRORS.md)). Try them in the browser: [https://mt4.mrpc.pro/apiui](https://mt4.mrpc.pro/apiui).

## Calling a method

Every method below is a method of the generated stub of its service (`mt4_term_api.{Service}Grpc`). Get it with the
API key and terminal id from your `MT4Account`:

```java
var marketInfo = account.authorized(MarketInfoGrpc.newBlockingStub(account.getChannel()));
var reply = marketInfo.quote(QuoteRequest.newBuilder().setSymbol("EURUSD").build());
if (reply.hasError()) throw new IllegalStateException(reply.getError().getErrorMessage());
System.out.println(reply.getData().getBid() + " / " + reply.getData().getAsk());
```

Field `foo_bar` is `getFooBar()` / `setFooBar(value)` on the builder; a repeated field is `getFooBarList()` /
`addFooBar(value)` / `addAllFooBar(list)`. A server stream is a blocking stub's `Iterator` or an async stub
(`newStub`) with a `StreamObserver`.

## AccountHelper

### AccountHelper.AccountSummary

Some information about account

- gRPC: `mt4_term_api.AccountHelper/AccountSummary` (unary)
- REST: `GET /AccountSummary`

**Request** `AccountSummaryRequest`

_No fields._

**Reply `data`** [AccountSummaryData](messages.md#accountsummarydata)

### AccountHelper.OpenedOrders

List of opened orders

- gRPC: `mt4_term_api.AccountHelper/OpenedOrders` (unary)
- REST: `GET /OpenedOrders`

**Request** `OpenedOrdersRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `sort_type` | `getSortType()` | [EnumOpenedOrderSortType](enums.md#enumopenedordersorttype) |  |

**Reply `data`** [OpenedOrdersData](messages.md#openedordersdata)

### AccountHelper.OpenedOrdersTickets

List of opened orders tickets

- gRPC: `mt4_term_api.AccountHelper/OpenedOrdersTickets` (unary)
- REST: `GET /OpenedOrdersTickets`

**Request** `OpenedOrdersTicketsRequest`

_No fields._

**Reply `data`** [OpenedOrdersTicketsData](messages.md#openedordersticketsdata)

### AccountHelper.OrdersHistory

Orders history with pagination and open date filters

- gRPC: `mt4_term_api.AccountHelper/OrdersHistory` (unary)
- REST: `GET /OrdersHistory`

**Request** `OrdersHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `input_sort_mode` | `getInputSortMode()` | [EnumOrderHistorySortType](enums.md#enumorderhistorysorttype) |  |
| `input_from` | `getInputFrom()` | `google.protobuf.Timestamp` | Start of the range of order OPEN times (inclusive) (broker server time). Unset = no lower bound. |
| `input_from_utc` | `getInputFromUtc()` | `google.protobuf.Timestamp` | input_from in UTC: send either input_from or input_from_utc, not both. |
| `input_to` | `getInputTo()` | `google.protobuf.Timestamp` | End of the range of order OPEN times (inclusive) (broker server time). Unset = no upper bound. |
| `input_to_utc` | `getInputToUtc()` | `google.protobuf.Timestamp` | input_to in UTC: send either input_to or input_to_utc, not both. |
| `page_number` | `getPageNumber()` | `int32` | Page-number paging (legacy): 1-based, needs itemsPerPage too. 0 = not used |
| `items_per_page` | `getItemsPerPage()` | `int32` | Page-number paging (legacy): rows per page, needs pageNumber too. 0 = not used |
| `page_token` | `getPageToken()` | `string` | Cursor paging (preferred, stable): the next_page_token of the previous page; empty for the first page. Send it back with the same inputFrom, inputTo and inputSortMode. Cursor paging is on when page_token or page_size is set; it cannot be combined with pageNumber/itemsPerPage (ORDERS_HISTORY_PAGING_INVALID). |
| `page_size` | `getPageSize()` | `int32` | Cursor paging: orders per page, 1..5000 (more is clamped to 5000). 0 = 1000 when page_token is set. |

**Reply `data`** [OrdersHistoryData](messages.md#ordershistorydata)

### AccountHelper.SymbolParamsMany

Full information about exact symbol, or all symbols

- gRPC: `mt4_term_api.AccountHelper/SymbolParamsMany` (unary)
- REST: `GET /SymbolParamsMany`

**Request** `SymbolParamsManyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_name` | `getSymbolName()` | `string` |  |

**Reply `data`** [SymbolParamsManyData](messages.md#symbolparamsmanydata)

### AccountHelper.TickValueWithSize

Tick value as array. Name the symbols (symbolNames); with no names the reply covers the symbols currently in Market Watch, never the whole server list.

- gRPC: `mt4_term_api.AccountHelper/TickValueWithSize` (unary)
- REST: `GET /TickValueWithSize`

**Request** `TickValueWithSizeRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_names` | `getSymbolNamesList()` | repeated `string` | Symbols to read, by exact name (REST: repeated query parameter symbolNames). Each named symbol is added to Market Watch when it is not there yet, then read. At most 1000 names per request; a longer list is answered with an error. EMPTY list = every symbol currently in Market Watch. It is NOT "all symbols of the server": the terminal's Market Watch holds at most ~1000 symbols, so serving the server's list (12,000+ at some brokers) would mean adding all of them to Market Watch and changing the user's selection. Name the symbols you need. |

**Reply `data`** [TickValueWithSizeData](messages.md#tickvaluewithsizedata)

## Accounts

============================================================================ Accounts: the MT4 accounts (terminals) that belong to your API key. This is the call to make when you need the terminal ids of accounts that already exist - started from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills a terminal. Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists MT4 accounts). ============================================================================

### Accounts.List

Lists the accounts of the API key sent in the APIKey header, one page per call. Pass the next_page_token of a page back as page_token to continue; an empty next_page_token means that was the last page. [DefaultValues] { "includeStopped": "false", "pageSize": "200" }

- gRPC: `mt4_term_api.Accounts/List` (unary)
- REST: `GET /Accounts`

**Request** `AccountsListRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `state` | `getState()` | `string` | Only accounts in this state, e.g. "Connected", "Syncing", "Unreachable", "WrongPassword", "Suspended" (the vocabulary of ConnectState.state, compared without regard to case). Empty = every state. |
| `login` | `getLogin()` | `uint64` | Only the account with this login number. 0 = every login. |
| `include_stopped` | `getIncludeStopped()` | `bool` | false (default): only accounts that are queued, starting or running, or that stopped on their own (Unreachable, WrongPassword, Suspended, Expired) and can be started again. true: also accounts that were stopped by the user, by an API call or by an administrator. Deleted accounts are never listed. |
| `page_size` | `getPageSize()` | `int32` | Accounts per page, 1..1000. 0 = 200. A larger value is clamped, a negative one is rejected with ACCOUNTS_PAGE_SIZE_INVALID. |
| `page_token` | `getPageToken()` | `string` | The next_page_token of the previous page; empty for the first page. Must be sent with the same state, login and include_stopped. Undecodable -> ACCOUNTS_PAGE_TOKEN_INVALID. |

**Reply `data`** [AccountsListData](messages.md#accountslistdata)

## Connection

### Connection.ConnectEx

Creates terminal connection to the MT5 server by MT cluster name, waits for connection and returns Guid of it [DefaultValues] { "user": "1124213727", "password": "1tjvjck", "mtClusterName": "FreshForex-Demo" }

- gRPC: `mt4_term_api.Connection/ConnectEx` (unary)
- REST: `GET /ConnectEx`

**Request** `ConnectExRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `mt_cluster_name` | `getMtClusterName()` | `string` | MT cluster name. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) | EA files to add to terminal instance [Optional] |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 60) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectData](messages.md#connectdata)

### Connection.Connect

Creates terminal connection to the MT4 server and returns Guid of it [DefaultValues] { "user": "501401178", "password": "v8gctta", "host": "mt4-demo.roboforex.com", "port": "443", "baseChartSymbol": "EURUSD", "waitForTerminalIsAlive": "true" }

- gRPC: `mt4_term_api.Connection/Connect` (unary)
- REST: `GET /Connect`

**Request** `ConnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `host` | `getHost()` | `string` | Host - ip address or dns name. [Required] |
| `port` | `getPort()` | `int32` | Port. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 60) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectData](messages.md#connectdata)

### Connection.ConnectProxy

Connect to account with user, password, host, port. [DefaultValues] { "user": "500476959", "password": "ehj4bod", "host": "mt4-demo.roboforex.com", "port": "443", "proxyUser": "ProxyUser123", "proxyPassword": "qwerty123", "proxyHost": "65.108.126.217", "proxyPort": "1080", "proxyType": "Socks5" }

- gRPC: `mt4_term_api.Connection/ConnectProxy` (unary)
- REST: `GET /ConnectProxy`

**Request** `ConnectProxyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `host` | `getHost()` | `string` | Host - ip address or dns name. [Required] |
| `port` | `getPort()` | `int32` | Port. [Required] |
| `proxyUser` | `getProxyUser()` | `string` | Proxy user [Required] |
| `proxyPassword` | `getProxyPassword()` | `string` | Proxy password [Required] |
| `proxyHost` | `getProxyHost()` | `string` | Proxy server ip address [Required] |
| `proxyPort` | `getProxyPort()` | `uint32` | Proxy port [Required] |
| `proxyType` | `getProxyType()` | [ProxyTypes](enums.md#proxytypes) | Proxy type. Put 'Socks5' for default parameters [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 60) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectProxyData](messages.md#connectproxydata)

### Connection.CheckConnect

Checks if terminal connection to MT4 server is alive

- gRPC: `mt4_term_api.Connection/CheckConnect` (unary)
- REST: `GET /CheckConnect`

**Request** `CheckConnectRequest`

_No fields._

**Reply `data`** [CheckConnectData](messages.md#checkconnectdata)

### Connection.ConnectState

Returns detailed live connection state of this terminal instance

- gRPC: `mt4_term_api.Connection/ConnectState` (unary)
- REST: `GET /ConnectState`

**Request** `ConnectStateRequest`

_No fields._

**Reply `data`** [ConnectStateData](messages.md#connectstatedata)

### Connection.OnConnectState

Streams real-time connection state changes for specified or visible terminals

- gRPC: `mt4_term_api.Connection/OnConnectState` (server stream)
- REST: `GET /OnConnectState` (also a WebSocket)

**Request** `OnConnectStateRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_ids` | `getTerminalIdsList()` | repeated `string` |  |

**Each message (`data`)** [ConnectStateData](messages.md#connectstatedata)

### Connection.ConnectionStatus

Deprecated alias for ConnectState

- gRPC: `mt4_term_api.Connection/ConnectionStatus` (unary)
- REST: `GET /ConnectionStatus`

**Request** `ConnectionStatusRequest`

_No fields._

**Reply `data`** [ConnectionStatusData](messages.md#connectionstatusdata)

### Connection.Disconnect

Close terminal connection to MT4 server

- gRPC: `mt4_term_api.Connection/Disconnect` (unary)
- REST: `GET /Disconnect`

**Request** `DisconnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `reason` | `getReason()` | `string` |  |
| `delete` | `getDelete()` | `bool` |  |

**Reply `data`** [DisconnectData](messages.md#disconnectdata)

### Connection.Reconnect

If you need to recreate terminal instance with the same id

- gRPC: `mt4_term_api.Connection/Reconnect` (unary)
- REST: `GET /Reconnect`

**Request** `ReconnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `force_reconnection` | `getForceReconnection()` | `bool` | Recreate a terminal instance even if current instance is alive |

**Reply `data`** [ReconnectData](messages.md#reconnectdata)

### Connection.ConnectByToken

Rebuild a terminal instance from a previously-saved token_details_mt4 row. The id header names the terminal to bring back; every credential (User/Password/Server or Host+Port, proxy settings, servers.dat bytes, PfxFile, HardwareId, Build) is read from the DB. Same reply as ConnectEx so callers do not need a separate result path.

- gRPC: `mt4_term_api.Connection/ConnectByToken` (unary)
- REST: `GET /ConnectByToken`

**Request** `ConnectByTokenRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | override how long to wait for the terminal (default 60) [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply `data`** [ConnectData](messages.md#connectdata)

### Connection.GetBrokerServersByBrokerName

- gRPC: `mt4_term_api.Connection/GetBrokerServersByBrokerName` (unary)
- REST: `GET /GetBrokerServersByBrokerName`

**Request** `GetBrokerServersByBrokerNameRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `BrokerName` | `getBrokerName()` | `string` |  |

**Reply `data`** [GetBrokerServersByBrokerNameData](messages.md#getbrokerserversbybrokernamedata)

### Connection.GetId

Generates a deterministic GUID from user and password. The same user/password combination always produces the same GUID. Use this GUID as the 'id' header for Connect and other endpoints. [DefaultValues] { "user": "1124213727", "password": "1tjvjck" }

- gRPC: `mt4_term_api.Connection/GetId` (unary)
- REST: `GET /GetId`

**Request** `GetIdRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `string` | Account number [Required] |
| `password` | `getPassword()` | `string` | Account password [Required] |

**Reply `data`** [GetIdData](messages.md#getiddata)

### Connection.Screenshot

Captures a screenshot of the terminal instance window. Handled by Terminal Manager: the per-terminal in-process responder grabs the MT4 window and the manager returns the image. Requires 'id' header.

- gRPC: `mt4_term_api.Connection/Screenshot` (unary)
- REST: `GET /Screenshot`

**Request** `ScreenshotRequest`

_No fields._

**Reply `data`** [ScreenshotData](messages.md#screenshotdata)

### Connection.ConnectStream

Same as Connect but streams real-time progress events (incl. live Journal/ Experts log lines) while the connection is established. Optional 'id' header — automatically generated by the server if omitted. Swagger does not support streaming — use /connect-stream interactive viewer.

- gRPC: `mt4_term_api.Connection/ConnectStream` (server stream)
- REST: `GET /ConnectStream` (also a WebSocket)

**Request** `ConnectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `host` | `getHost()` | `string` | Host - ip address or dns name. [Required] |
| `port` | `getPort()` | `int32` | Port. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 60) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply** `ConnectStreamEvent`

| Field | Java | Type | Description |
|---|---|---|---|
| `step` | `getStep()` | `string` | Step name: "Init", "Validation", "ColdStart-CreateInstance", "ColdStart-ReadinessCheck", "Journal", "Experts", "Complete", "Error", ... |
| `message` | `getMessage()` | `string` | Human-readable message with details (for Journal/Experts steps: the log line) |
| `level` | `getLevel()` | `string` | Log level: INFO, WARN, ERROR |
| `elapsed_ms` | `getElapsedMs()` | `int64` | Elapsed milliseconds since start |
| `timestamp` | `getTimestamp()` | `google.protobuf.Timestamp` | Timestamp of this event |
| `is_final` | `getIsFinal()` | `bool` | True only on the final event (success or error) |
| `connect_data` | `getConnectData()` | [ConnectData](messages.md#connectdata) | Populated only on the final success event |
| `error_data` | `getErrorData()` | [Error](messages.md#error) | Populated only on the final error event |

### Connection.ConnectExStream

Same as ConnectEx but streams real-time progress events (incl. live Journal/ Experts log lines) while the connection is established. Optional 'id' header — automatically generated by the server if omitted. Swagger does not support streaming — use /connect-stream interactive viewer. [DefaultValues] { "user": "1124213727", "password": "1tjvjck", "mtClusterName": "FreshForex-Demo" }

- gRPC: `mt4_term_api.Connection/ConnectExStream` (server stream)
- REST: `GET /ConnectExStream` (also a WebSocket)

**Request** `ConnectExRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `user` | `getUser()` | `uint64` | Account number. [Required] |
| `password` | `getPassword()` | `string` | Password. [Required] |
| `mt_cluster_name` | `getMtClusterName()` | `string` | MT cluster name. [Required] |
| `experts_to_add` | `getExpertsToAddList()` | repeated [ExpertAdviser](messages.md#expertadviser) | EA files to add to terminal instance [Optional] |
| `timeout_seconds` | `getTimeoutSeconds()` | `uint32` | Time waiting for terminal being connected (default 60) [Optional] |
| `name` | `getName()` | `string` | Optional terminal name or creator identifier [Optional] |
| `expiration` | `getExpiration()` | `uint32` | Optional terminal lifetime in minutes. 0 (default) = infinite (trial max 15 min). [Optional] |

**Reply** `ConnectStreamEvent`

| Field | Java | Type | Description |
|---|---|---|---|
| `step` | `getStep()` | `string` | Step name: "Init", "Validation", "ColdStart-CreateInstance", "ColdStart-ReadinessCheck", "Journal", "Experts", "Complete", "Error", ... |
| `message` | `getMessage()` | `string` | Human-readable message with details (for Journal/Experts steps: the log line) |
| `level` | `getLevel()` | `string` | Log level: INFO, WARN, ERROR |
| `elapsed_ms` | `getElapsedMs()` | `int64` | Elapsed milliseconds since start |
| `timestamp` | `getTimestamp()` | `google.protobuf.Timestamp` | Timestamp of this event |
| `is_final` | `getIsFinal()` | `bool` | True only on the final event (success or error) |
| `connect_data` | `getConnectData()` | [ConnectData](messages.md#connectdata) | Populated only on the final success event |
| `error_data` | `getErrorData()` | [Error](messages.md#error) | Populated only on the final error event |

## Logs

Provides access to terminal log content (Journal and Experts tabs). Reads log entries directly from the MT4 terminal GUI. Requires 'id' header with the terminal connection GUID returned by Connect.

### Logs.Journal

Returns log entries from the terminal Journal tab. The Journal tab contains system messages about terminal connection status, network activity, server synchronization and other internal events. Works regardless of which tab is currently active in the terminal UI.

- gRPC: `mt4_term_api.Logs/Journal` (unary)
- REST: `GET /Logs/Journal`

**Request** `JournalRequest`

_No fields._

**Reply `data`** [GetTerminalJournalData](messages.md#getterminaljournaldata)

### Logs.OnJournal

Streams log entries from the terminal Journal tab in real-time. Requires 'id' header with the terminal connection GUID returned by Connect.

- gRPC: `mt4_term_api.Logs/OnJournal` (server stream)
- REST: `GET /Logs/OnJournal` (also a WebSocket)

**Request** `OnJournalRequest`

_No fields._

**Each message (`data`)** [GetTerminalJournalData](messages.md#getterminaljournaldata)

### Logs.Experts

Returns log entries from the terminal Experts tab. The Experts tab contains messages from Expert Advisors (EAs), scripts and indicators including Print() output, initialization/deinitialization events and runtime errors. Works regardless of which tab is currently active in the terminal UI.

- gRPC: `mt4_term_api.Logs/Experts` (unary)
- REST: `GET /Logs/Experts`

**Request** `JournalRequest`

_No fields._

**Reply `data`** [GetTerminalJournalData](messages.md#getterminaljournaldata)

### Logs.OnExperts

Streams log entries from the terminal Experts tab in real-time. Requires 'id' header with the terminal connection GUID returned by Connect.

- gRPC: `mt4_term_api.Logs/OnExperts` (server stream)
- REST: `GET /Logs/OnExperts` (also a WebSocket)

**Request** `OnJournalRequest`

_No fields._

**Each message (`data`)** [GetTerminalJournalData](messages.md#getterminaljournaldata)

## DemoAccount

Demo account creation service. Automates the MT4 "Open an Account" wizard via Win32 GUI automation (proxied to the terminal's Gui service). Does NOT require 'id' header — auto-picks any available terminal.

### DemoAccount.OpenDemoAccount

Open a demo account. Full wizard flow: search -> select -> fill form -> register. [DefaultValues] { "company": "MetaQuotes Ltd.", "firstName": "Test", "lastName": "User", "email": "test@test.com", "phone": "+1234567890", "timeoutSeconds": "60" }

- gRPC: `mt4_term_api.DemoAccount/OpenDemoAccount` (unary)
- REST: `GET /DemoAccount/Open`

**Request** `GuiDemoOpenAccountRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `company` | `getCompany()` | `string` |  |
| `first_name` | `getFirstName()` | `string` |  |
| `last_name` | `getLastName()` | `string` |  |
| `email` | `getEmail()` | `string` |  |
| `phone` | `getPhone()` | `string` |  |
| `server` | `getServer()` | `string` |  |
| `account_type` | `getAccountType()` | `string` |  |
| `timeout_seconds` | `getTimeoutSeconds()` | `int32` |  |

**Reply** `GuiDemoOpenAccountReply`

| Field | Java | Type | Description |
|---|---|---|---|
| `result_code` | `getResultCode()` | `int32` |  |
| `login` | `getLogin()` | `string` |  |
| `password` | `getPassword()` | `string` |  |
| `investor` | `getInvestor()` | `string` |  |
| `server` | `getServer()` | `string` |  |
| `debug_log` | `getDebugLog()` | `string` |  |

## MarketInfo

### MarketInfo.Quote

Get quote

- gRPC: `mt4_term_api.MarketInfo/Quote` (unary)
- REST: `GET /Quote`

**Request** `QuoteRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | optional int32 not_older_milliseconds = 2; |

**Reply `data`** [QuoteData](messages.md#quotedata)

### MarketInfo.QuoteMany

Latest quote for the specified symbol

- gRPC: `mt4_term_api.MarketInfo/QuoteMany` (unary)
- REST: `GET /QuoteMany`

**Request** `QuoteManyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbols` | `getSymbolsList()` | repeated `string` |  |

**Reply `data`** [QuoteManyData](messages.md#quotemanydata)

### MarketInfo.Symbols

- gRPC: `mt4_term_api.MarketInfo/Symbols` (unary)
- REST: `GET /Symbols`

**Request** `SymbolsRequest`

_No fields._

**Reply `data`** [SymbolsData](messages.md#symbolsdata)

### MarketInfo.QuoteHistory

DEPRECATED: use QuoteHistory.Bars (mt4-term-api-quote-history.proto): paged, bounded, oldest first.

- gRPC: `mt4_term_api.MarketInfo/QuoteHistory` (unary)
- REST: `GET /QuoteHistory`

**Request** `QuoteHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` |  |
| `timeframe` | `getTimeframe()` | [ENUM_QUOTE_HISTORY_TIMEFRAME](enums.md#enum_quote_history_timeframe) |  |
| `fromTime` | `getFromTime()` | `google.protobuf.Timestamp` | Start of the range of bars (broker server time). |
| `fromTimeUtc` | `getFromTimeUtc()` | `google.protobuf.Timestamp` | fromTime in UTC: send either fromTime or fromTimeUtc, not both. |
| `toTime` | `getToTime()` | `google.protobuf.Timestamp` | End of the range of bars (broker server time). |
| `toTimeUtc` | `getToTimeUtc()` | `google.protobuf.Timestamp` | toTime in UTC: send either toTime or toTimeUtc, not both. |

**Reply `data`** [QuoteHistoryData](messages.md#quotehistorydata)

### MarketInfo.SymbolSelect

Selects a symbol in the Market Watch window or removes a symbol from the window https://docs.mql4.com/marketinformation/symbolselect

- gRPC: `mt4_term_api.MarketInfo/SymbolSelect` (unary)
- REST: `GET /SymbolSelect`

**Request** `SymbolSelectRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Symbol name |
| `select` | `getSelect()` | `bool` | Switch. If the value is false, a symbol should be removed from MarketWatch, otherwise a symbol should be selected in this window. A symbol can't be removed if the symbol chart is open, or there are open positions for this symbol |

**Reply `data`** [SymbolSelectData](messages.md#symbolselectdata)

## QuoteHistory

============================================================================ Quote history: the bars a chart is drawn from, for one symbol - returned by PAGE, oldest first. MetaTrader 4 keeps no tick history, so there is no Ticks call here; the MT5 API has one. Bars walks a time range forward. A page carries at most page_size bars and, when the range holds more, a next_page_token; passing that token back (same symbol, timeframe and range) returns the next page. An empty next_page_token means the range is exhausted. Tokens are opaque: a client stores and returns them, nothing else. History lives in the terminal and is fetched from the broker on demand. The first request for a symbol or a period the terminal has not loaded yet can take a few seconds, and when the broker has not delivered it within that time the call answers with an error whose error_code is QUOTE_HISTORY_NOT_READY - ask again a moment later. Every call needs the terminal id header ("id"), like every other call. ============================================================================

### QuoteHistory.Bars

OHLC bars (candles) of one symbol and timeframe, oldest first, by page.

- gRPC: `mt4_term_api.QuoteHistory/Bars` (unary)
- REST: `GET /QuoteHistory/Bars`

**Request** `QuoteHistoryBarsRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Required. Broker symbol name, e.g. "EURUSD". |
| `timeframe` | `getTimeframe()` | [QUOTE_HISTORY_TIMEFRAME](enums.md#quote_history_timeframe) | Required. |
| `from_time` | `getFromTime()` | `int64` | Required. Unix seconds (UTC) of the oldest bar wanted, inclusive. A time before the broker's first bar is clamped to that first bar. |
| `from_time_utc` | `getFromTimeUtc()` | `int64` | from_time in UTC: send either from_time or from_time_utc, not both. |
| `to_time` | `getToTime()` | `int64` | Unix seconds (UTC) of the newest bar wanted, inclusive. 0 = now. |
| `to_time_utc` | `getToTimeUtc()` | `int64` | to_time in UTC: send either to_time or to_time_utc, not both. |
| `page_size` | `getPageSize()` | `int32` | Bars per page, 1..5000. 0 = 1000. |
| `page_token` | `getPageToken()` | `string` | The next_page_token of the previous page, to continue; empty for the first page. Must be used with the same symbol, timeframe and range. |

**Reply `data`** [QuoteHistoryBarsData](messages.md#quotehistorybarsdata)

## ServerTimezone

============================================================================ ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from. Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it knows (the terminal is not asked unless nothing is known yet), so it is cheap to call. ============================================================================

### ServerTimezone.ServerTimezone

The broker server's UTC offset of the terminal in the "id" header: live (read by this terminal from a fresh quote) or the last one validated for the same broker server (an account whose market is closed has no quote to read it from - MT4 only; MT5 reads its server clock directly and is always live).

- gRPC: `mt4_term_api.ServerTimezone/ServerTimezone` (unary)
- REST: `GET /ServerTimezone`

**Request** `ServerTimezoneRequest`

_No fields._

**Reply `data`** [ServerTimezoneData](messages.md#servertimezonedata)

## SubscriptionService

### SubscriptionService.OnTrade

Streams real-time order/trade events. Requires 'id' header returned by Connect/ConnectEx. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt4_term_api.SubscriptionService/OnTrade` (server stream)
- REST: `GET /OnTrade` (also a WebSocket)

**Request** `OnTradeRequest`

_No fields._

**Each message (`data`)** [OnTradeData](messages.md#ontradedata)

### SubscriptionService.OnOpenedOrdersTickets

Streams the tickets of currently opened orders as they change. Requires 'id' header returned by Connect/ConnectEx. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt4_term_api.SubscriptionService/OnOpenedOrdersTickets` (server stream)
- REST: `GET /OnOpenedOrdersTickets` (also a WebSocket)

**Request** `OnOpenedOrdersTicketsRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `pull_interval_milliseconds` | `getPullIntervalMilliseconds()` | `int32` |  |

**Each message (`data`)** [OnOpenedOrdersTicketsData](messages.md#onopenedordersticketsdata)

### SubscriptionService.OnOpenedOrdersProfit

Streams the live profit of currently opened orders. Requires 'id' header returned by Connect/ConnectEx. Swagger does not support streaming — use /subscription-stream interactive viewer.

- gRPC: `mt4_term_api.SubscriptionService/OnOpenedOrdersProfit` (server stream)
- REST: `GET /OnOpenedOrdersProfit` (also a WebSocket)

**Request** `OnOpenedOrdersProfitRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `timer_period_milliseconds` | `getTimerPeriodMilliseconds()` | `int32` |  |

**Each message (`data`)** [OnOpenedOrdersProfitData](messages.md#onopenedordersprofitdata)

### SubscriptionService.OnSymbolTick

Streams real-time ticks for the given symbols. Requires 'id' header returned by Connect/ConnectEx. Swagger does not support streaming — use /subscription-stream interactive viewer. [DefaultValues] { "symbolNames": "EURUSD,BTCUSD" }

- gRPC: `mt4_term_api.SubscriptionService/OnSymbolTick` (server stream)
- REST: `GET /OnSymbolTick` (also a WebSocket)

**Request** `OnSymbolTickRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_names` | `getSymbolNamesList()` | repeated `string` |  |

**Each message (`data`)** [OnSymbolTickData](messages.md#onsymboltickdata)

## TradeStats

### TradeStats.TradeStats

Trading statistics calculated from order history and open orders [DefaultValues] { "from": "2024-01-01T00:00:00" }

- gRPC: `mt4_term_api.TradeStats/TradeStats` (unary)
- REST: `GET /TradeStats`

**Request** `TradeStatsRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |

**Reply `data`** [StatsData](messages.md#statsdata)

### TradeStats.TradeStatsEquityHistory

Trading statistics with equity history chart points [DefaultValues] { "from": "2024-01-01T00:00:00", "excludeSameBars": true }

- gRPC: `mt4_term_api.TradeStats/TradeStatsEquityHistory` (unary)
- REST: `GET /TradeStatsEquityHistory`

**Request** `TradeStatsEquityHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |
| `exclude_same_bars` | `getExcludeSameBars()` | `bool` | Return short version without equal bars |

**Reply `data`** [StatsWithChartsData](messages.md#statswithchartsdata)

### TradeStats.EquityHistory

Equity history curve points with drawdown calculation [DefaultValues] { "from": "2024-10-01T00:00:00", "excludeSameBars": true }

- gRPC: `mt4_term_api.TradeStats/EquityHistory` (unary)
- REST: `GET /EquityHistory`

**Request** `EquityHistoryRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |
| `exclude_same_bars` | `getExcludeSameBars()` | `bool` | Return short version without equal bars |

**Reply `data`** [EquityHistoryData](messages.md#equityhistorydata)

### TradeStats.TradeUnrealizedPL

Maximum and minimum unrealized profit/loss per trade [DefaultValues] { "from": "2024-01-01T00:00:00" }

- gRPC: `mt4_term_api.TradeStats/TradeUnrealizedPL` (unary)
- REST: `GET /TradeUnrealizedPL`

**Request** `TradeUnrealizedPLRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `from` | `getFrom()` | `string` | From datetime in format: yyyy-MM-ddTHH:mm:ss |
| `from_utc` | `getFromUtc()` | `string` | from in UTC: send either from or from_utc, not both. |

**Reply `data`** [TradeUnrealizedPLData](messages.md#tradeunrealizedpldata)

## TradingHelper

### TradingHelper.OrderSend

Send market or pending order

- gRPC: `mt4_term_api.TradingHelper/OrderSend` (unary)
- REST: `GET /OrderSend`

**Request** `OrderSendRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` | Required, NOT EMPTY string |
| `operation_type` | `getOperationType()` | [OrderSendOperationType](enums.md#ordersendoperationtype) | required |
| `volume` | `getVolume()` | `double` | Required |
| `price` | `getPrice()` | `double` | NOT required in case of Mt4_OP_BUY = 0; Mt4_OP_SELL = 1; |
| `slippage` | `getSlippage()` | `int32` | not required default 0 |
| `stoploss` | `getStoploss()` | `double` | not required default 0 |
| `takeprofit` | `getTakeprofit()` | `double` | not required default 0 |
| `comment` | `getComment()` | `string` | not required default null |
| `magic_number` | `getMagicNumber()` | `int32` | not required default 0 |
| `expiration` | `getExpiration()` | `google.protobuf.Timestamp` | not required default null |
| `expiration_utc` | `getExpirationUtc()` | `google.protobuf.Timestamp` | expiration in UTC: send either expiration or expiration_utc, not both. |

**Reply `data`** [OrderSendData](messages.md#ordersenddata)

### TradingHelper.OrderModify

Modify market or pending order

- gRPC: `mt4_term_api.TradingHelper/OrderModify` (unary)
- REST: `GET /OrderModify`

**Request** `OrderModifyRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `order_ticket` | `getOrderTicket()` | `int32` |  |
| `new_price` | `getNewPrice()` | `double` |  |
| `new_stop_loss` | `getNewStopLoss()` | `double` |  |
| `new_take_profit` | `getNewTakeProfit()` | `double` |  |
| `new_expiration` | `getNewExpiration()` | `google.protobuf.Timestamp` | New expiration of a pending order (broker server time). Unset = the expiration is not changed. |
| `new_expiration_utc` | `getNewExpirationUtc()` | `google.protobuf.Timestamp` | new_expiration in UTC: send either new_expiration or new_expiration_utc, not both. |

**Reply `data`** [OrderModifyData](messages.md#ordermodifydata)

### TradingHelper.OrderCloseDelete

Close market or pending order

- gRPC: `mt4_term_api.TradingHelper/OrderCloseDelete` (unary)
- REST: `GET /OrderCloseDelete`

**Request** `OrderCloseDeleteRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `order_ticket` | `getOrderTicket()` | `int32` |  |
| `lots` | `getLots()` | `double` |  |
| `closing_price` | `getClosingPrice()` | `double` |  |
| `slippage` | `getSlippage()` | `int32` |  |

**Reply `data`** [OrderCloseDeleteData](messages.md#orderclosedeletedata)

### TradingHelper.OrderCloseBy

Close market order with opposite market order

- gRPC: `mt4_term_api.TradingHelper/OrderCloseBy` (unary)
- REST: `GET /OrderCloseBy`

**Request** `OrderCloseByRequest`

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket_to_close` | `getTicketToClose()` | `int32` |  |
| `opposite_ticket_closing_by` | `getOppositeTicketClosingBy()` | `int32` |  |

**Reply `data`** [OrderCloseByData](messages.md#orderclosebydata)
