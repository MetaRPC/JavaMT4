# Messages

The messages of the [API methods](methods.md) (package `mt4_term_api`), generated from the protos.

## AccountSummaryData

| Field | Java | Type | Description |
|---|---|---|---|
| `account_login` | `getAccountLogin()` | `int64` |  |
| `account_balance` | `getAccountBalance()` | `double` |  |
| `account_equity` | `getAccountEquity()` | `double` |  |
| `account_user_name` | `getAccountUserName()` | `string` |  |
| `account_leverage` | `getAccountLeverage()` | `int64` |  |
| `account_trade_mode` | `getAccountTradeMode()` | [EnumAccountTradeMode](enums.md#enumaccounttrademode) |  |
| `account_company_name` | `getAccountCompanyName()` | `string` |  |
| `account_currency` | `getAccountCurrency()` | `string` |  |
| `server_time` | `getServerTime()` | `google.protobuf.Timestamp` | The server's current time at the moment of the call (broker server time). Unset while the account is not connected yet and no offset is known. |
| `server_time_utc` | `getServerTimeUtc()` | `google.protobuf.Timestamp` | server_time in UTC (the same moment; server_time is in the broker server's time zone). |
| `account_credit` | `getAccountCredit()` | `double` |  |
| `is_investor` | `getIsInvestor()` | `bool` |  |
| `utc_server_time_shift_minutes` | `getUtcServerTimeShiftMinutes()` | `int32` | The broker server's offset from UTC in whole minutes (server_utc_offset_seconds / 60), a multiple of 15. Information, never converted. 0 while the account is not connected yet. |
| `server_utc_offset_seconds` | `getServerUtcOffsetSeconds()` | `int32` | The broker server's offset from UTC in seconds, positive = ahead of UTC: validated by the terminal (a multiple of 15 minutes). Information, never converted; it is the offset behind every *_utc field of this API. ABSENT (has_server_utc_offset_seconds is false) while it is not known yet: the account is not connected, or it is connected but the market is closed and no price quote has arrived since the terminal started - MT4 learns the offset from a live quote. Absent never means UTC+0 (a UTC+0 broker sends 0 and has it present). The *_utc fields stay unset until it is known. A connected terminal whose computer clock is provably wrong answers SERVER_TIMEZONE_UNDETERMINED instead of data. The current offset is applied to every value, so history from before the broker's last daylight-saving change is off by the daylight-saving step (usually 3600 s). |

## AccountsListData

| Field | Java | Type | Description |
|---|---|---|---|
| `accounts` | `getAccountsList()` | repeated [AccountsListItem](messages.md#accountslistitem) |  |
| `next_page_token` | `getNextPageToken()` | `string` | Pass back as page_token for the next page; empty when this was the last page. |
| `total_count` | `getTotalCount()` | `int32` | Accounts matching the filters over all pages, at the moment of the call. |

## AccountsListItem

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_id` | `getTerminalId()` | `string` | The terminal id to send as the "id" header of every other call, in the form the API itself returns it: mt4_live_<32 hex>. |
| `terminal_guid` | `getTerminalGuid()` | `string` | The same identity as a hyphenated GUID. The API accepts either form in the "id" header. |
| `platform` | `getPlatform()` | `string` | "MT4". |
| `login` | `getLogin()` | `uint64` | Account (login) number. |
| `server` | `getServer()` | `string` | Broker server name, as given to ConnectEx. |
| `name` | `getName()` | `string` | Name given to the terminal when it was started; may be empty. |
| `state` | `getState()` | `string` | Current state of the account's terminal: Queued, Starting, Authorizing, Syncing, Connected, WrongPassword, Unreachable, Suspended, Expired or Stopped (see ConnectState). It is the state the cluster publishes for the terminal right now; a running terminal whose published state has not been refreshed for over a minute reads Unreachable. Only when nothing is published is the last stored state returned. "Unknown" appears for a record that has never had a state. |
| `is_connected` | `getIsConnected()` | `bool` | state == "Connected". |
| `started_utc` | `getStartedUtc()` | `google.protobuf.Timestamp` | When the account was first started (UTC; a platform time, not a broker time); a restart does not change it. Unset when the record has no start time. |
| `stopped_utc` | `getStoppedUtc()` | `google.protobuf.Timestamp` | When it last stopped (UTC; a platform time, not a broker time). Unset while it is running. |
| `stop_reason` | `getStopReason()` | `string` | Why it stopped or is not running (for example "ByApi", "InvalidLogin", "Unreachable:StartupTimeout"); may be empty. Empty while the account is queued, starting or running. |
| `error_message` | `getErrorMessage()` | `string` | The last error recorded for the account, when its state is a failure; may be empty. |
| `expires_utc` | `getExpiresUtc()` | `google.protobuf.Timestamp` | When the terminal is set to expire (UTC; a platform time, not a broker time). Unset when no expiry is recorded for the account, which is the case for every account today. |

## AveragePipsUsdData

| Field | Java | Type | Description |
|---|---|---|---|
| `average_pips` | `getAveragePips()` | `double` |  |
| `average_usd` | `getAverageUsd()` | `double` |  |

## CheckConnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `unique_identifier` | `getUniqueIdentifier()` | `string` | Connection unique identifier. Reflects value from header 'mt-sticky-session-header' |
| `health_check` | `getHealthCheck()` | [TerminalHealthCheck](messages.md#terminalhealthcheck) | Connection health status |

## ConnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_instance_guid` | `getTerminalInstanceGuid()` | `string` | Connection unique identifier - use this in the 'mt-sticky-session-header' value |
| `terminal_type` | `getTerminalType()` | [TerminalType](enums.md#terminaltype) | Terminal type MT5 or MT4 (Temporary only MT5) |

## ConnectProxyData

| Field | Java | Type | Description |
|---|---|---|---|
| `unique_identifier` | `getUniqueIdentifier()` | `string` | Connection unique identifier - use this in the header 'mrpc-terminal-guid' value |
| `terminal_type` | `getTerminalType()` | [TerminalType](enums.md#terminaltype) | Terminal type MT5 or MT4 (Temporary only MT5) |

## ConnectStateData

| Field | Java | Type | Description |
|---|---|---|---|
| `id` | `getId()` | `string` |  |
| `state` | `getState()` | `string` | "Queued", "Starting", "Authorizing", "Syncing", "Connected", "WrongPassword", "Unreachable", "Stopped", "Expired" |
| `is_connected` | `getIsConnected()` | `bool` |  |
| `is_alive` | `getIsAlive()` | `bool` |  |
| `api_is_alive` | `getApiIsAlive()` | `bool` |  |
| `server` | `getServer()` | `string` |  |
| `account` | `getAccount()` | `uint64` |  |
| `error_message` | `getErrorMessage()` | `string` |  |
| `timestamp` | `getTimestamp()` | `google.protobuf.Timestamp` |  |

## ConnectStreamEvent

Event message streamed during ConnectStream / ConnectExStream.

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

## ConnectionStatusData

| Field | Java | Type | Description |
|---|---|---|---|
| `id` | `getId()` | `string` |  |
| `is_connected` | `getIsConnected()` | `bool` |  |
| `is_alive` | `getIsAlive()` | `bool` |  |
| `api_is_alive` | `getApiIsAlive()` | `bool` |  |
| `server` | `getServer()` | `string` |  |
| `account` | `getAccount()` | `uint64` |  |
| `error_message` | `getErrorMessage()` | `string` |  |
| `state` | `getState()` | `string` |  |

## DisconnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `unique_identifier` | `getUniqueIdentifier()` | `string` | Identifier of closed connection. Reflects value from header 'mt-sticky-session-header' |
| `full_life_time_seconds` | `getFullLifeTimeSeconds()` | `int64` |  |

## EquityHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `points` | `getPointsList()` | repeated [EquityPointData](messages.md#equitypointdata) |  |

## EquityPointData

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `google.protobuf.Timestamp` |  |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `balance` | `getBalance()` | `double` |  |
| `equity` | `getEquity()` | `double` |  |
| `balance_drawdown_raw` | `getBalanceDrawdownRaw()` | `double` |  |
| `balance_drawdown_relative` | `getBalanceDrawdownRelative()` | `double` |  |
| `equity_drawdown_raw` | `getEquityDrawdownRaw()` | `double` |  |
| `equity_drawdown_relative` | `getEquityDrawdownRelative()` | `double` |  |
| `realized_pl` | `getRealizedPl()` | `double` |  |
| `unrealized_pl` | `getUnrealizedPl()` | `double` |  |

## Error

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [ErrorType](enums.md#errortype) |  |
| `error_code` | `getErrorCode()` | `string` |  |
| `error_message` | `getErrorMessage()` | `string` |  |
| `mql_error_code` | `getMqlErrorCode()` | [MqlErrorCode](enums.md#mqlerrorcode) |  |
| `mql_error_int_code` | `getMqlErrorIntCode()` | `int32` |  |
| `mql_error_description` | `getMqlErrorDescription()` | `string` |  |
| `stack_trace` | `getStackTrace()` | `string` |  |
| `command_type_name` | `getCommandTypeName()` | `string` |  |
| `commandId` | `getCommandId()` | `int64` |  |
| `properties` | `getPropertiesList()` | repeated [ErrorProperty](messages.md#errorproperty) |  |

## ErrorProperty

| Field | Java | Type | Description |
|---|---|---|---|
| `error_arg_key` | `getErrorArgKey()` | `string` |  |
| `error_arg_value` | `getErrorArgValue()` | `string` |  |

## ExpectancyData

| Field | Java | Type | Description |
|---|---|---|---|
| `pips` | `getPips()` | `double` |  |
| `dollar` | `getDollar()` | `double` |  |

## ExpertAdviser

| Field | Java | Type | Description |
|---|---|---|---|
| `file_name` | `getFileName()` | `string` |  |
| `file_content` | `getFileContent()` | `bytes` | File content as a byte array |

## GetBrokerServersByBrokerNameData

| Field | Java | Type | Description |
|---|---|---|---|
| `result` | `getResultList()` | repeated [GetBrokerServersByBrokerNameData.BrokerResult](messages.md#getbrokerserversbybrokernamedatabrokerresult) |  |

## GetBrokerServersByBrokerNameData.BrokerResult

| Field | Java | Type | Description |
|---|---|---|---|
| `company` | `getCompany()` | `string` |  |
| `results` | `getResultsList()` | repeated [GetBrokerServersByBrokerNameData.BrokerServer](messages.md#getbrokerserversbybrokernamedatabrokerserver) |  |

## GetBrokerServersByBrokerNameData.BrokerServer

| Field | Java | Type | Description |
|---|---|---|---|
| `name` | `getName()` | `string` |  |
| `logo_url` | `getLogoUrl()` | `string` |  |
| `site` | `getSite()` | `string` |  |
| `access` | `getAccessList()` | repeated `string` |  |

## GetIdData

| Field | Java | Type | Description |
|---|---|---|---|
| `id` | `getId()` | `string` | Deterministic GUID generated from user+password |

## GetTerminalJournalData

| Field | Java | Type | Description |
|---|---|---|---|
| `rows` | `getRowsList()` | repeated [TerminalJournalRow](messages.md#terminaljournalrow) | List of log rows from the terminal tab |

## GuiDemoOpenAccountReply

| Field | Java | Type | Description |
|---|---|---|---|
| `result_code` | `getResultCode()` | `int32` |  |
| `login` | `getLogin()` | `string` |  |
| `password` | `getPassword()` | `string` |  |
| `investor` | `getInvestor()` | `string` |  |
| `server` | `getServer()` | `string` |  |
| `debug_log` | `getDebugLog()` | `string` |  |

## HistoryOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `comment` | `getComment()` | `string` |  |
| `commision` | `getCommision()` | `double` |  |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` | Expiration of a pending order (broker server time). Unset when the order has no expiration. |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC (the same moment; expiration_time is in the broker server's time zone). |
| `lots` | `getLots()` | `double` |  |
| `magic_number` | `getMagicNumber()` | `int32` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `ticket` | `getTicket()` | `int32` |  |
| `position_index` | `getPositionIndex()` | `int32` |  |
| `order_type` | `getOrderType()` | [OpenedOrderType](enums.md#openedordertype) |  |
| `sort_index` | `getSortIndex()` | `int32` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` | Open time of the order (broker server time). |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `close_time` | `getCloseTime()` | `google.protobuf.Timestamp` | Close time of the order (broker server time). Unset when the order is not closed. |
| `close_time_utc` | `getCloseTimeUtc()` | `google.protobuf.Timestamp` | close_time in UTC (the same moment; close_time is in the broker server's time zone). |
| `close_price` | `getClosePrice()` | `double` |  |
| `account_login` | `getAccountLogin()` | `int64` |  |

## HistoryQuote

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` |  |
| `index` | `getIndex()` | `int32` |  |
| `time` | `getTime()` | `google.protobuf.Timestamp` | Open time of the bar (broker server time). |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `open` | `getOpen()` | `double` |  |
| `close` | `getClose()` | `double` |  |
| `high` | `getHigh()` | `double` |  |
| `low` | `getLow()` | `double` |  |
| `real_volume` | `getRealVolume()` | `int64` |  |
| `tick_volume` | `getTickVolume()` | `int64` |  |
| `spread` | `getSpread()` | `int32` |  |

## LogFileInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `log_type` | `getLogType()` | [LogType](enums.md#logtype) |  |
| `full_path` | `getFullPath()` | `string` |  |
| `name` | `getName()` | `string` |  |
| `content` | `getContent()` | `string` |  |

## MarketTradeCountData

| Field | Java | Type | Description |
|---|---|---|---|
| `market_name` | `getMarketName()` | `string` |  |
| `count` | `getCount()` | `int32` |  |
| `lots` | `getLots()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `win_rate` | `getWinRate()` | `double` |  |
| `won_count` | `getWonCount()` | `int32` |  |
| `lost_count` | `getLostCount()` | `int32` |  |

## OnEventAccountInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `balance` | `getBalance()` | `double` |  |
| `credit` | `getCredit()` | `double` |  |
| `equity` | `getEquity()` | `double` |  |
| `margin` | `getMargin()` | `double` |  |
| `free_margin` | `getFreeMargin()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `margin_level` | `getMarginLevel()` | `double` |  |
| `login` | `getLogin()` | `int64` |  |

## OnOpenedOrdersProfitData

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [MT4_SUB_ENUM_EVENT_GROUP_TYPE](enums.md#mt4_sub_enum_event_group_type) |  |
| `opened_orders_with_profit_updated` | `getOpenedOrdersWithProfitUpdatedList()` | repeated [OnOpenedOrdersProfitOrderInfo](messages.md#onopenedordersprofitorderinfo) |  |
| `account_info` | `getAccountInfo()` | [OnEventAccountInfo](messages.md#oneventaccountinfo) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnOpenedOrdersProfitOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `int32` |  |
| `comment` | `getComment()` | `string` |  |
| `commission` | `getCommission()` | `double` |  |
| `expiration` | `getExpiration()` | `google.protobuf.Timestamp` |  |
| `expiration_utc` | `getExpirationUtc()` | `google.protobuf.Timestamp` | expiration in UTC (the same moment; expiration is in the broker server's time zone). |
| `lots` | `getLots()` | `double` |  |
| `magic_number` | `getMagicNumber()` | `int32` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` |  |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `order_profit` | `getOrderProfit()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `type` | `getType()` | [SUB_ORDER_OPERATION_TYPE](enums.md#sub_order_operation_type) |  |

## OnOpenedOrdersTicketsData

| Field | Java | Type | Description |
|---|---|---|---|
| `position_tickets` | `getPositionTicketsList()` | repeated `int32` |  |
| `pending_order_tickets` | `getPendingOrderTicketsList()` | repeated `int32` |  |
| `server_time` | `getServerTime()` | `google.protobuf.Timestamp` |  |
| `server_time_utc` | `getServerTimeUtc()` | `google.protobuf.Timestamp` | server_time in UTC (the same moment; server_time is in the broker server's time zone). |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnSymbolMqlTickInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `google.protobuf.Timestamp` | Time of the last prices update (Unix timestamp) |
| `time_utc` | `getTimeUtc()` | `google.protobuf.Timestamp` | time in UTC (the same moment; time is in the broker server's time zone). |
| `bid` | `getBid()` | `double` | Current Bid price |
| `ask` | `getAsk()` | `double` | Current Ask price |
| `last` | `getLast()` | `double` | Price of the last deal (Last) |
| `volume` | `getVolume()` | `uint64` | Volume for the current Last price |
| `time_msc` | `getTimeMsc()` | `int64` | Time of a price last update in milliseconds |
| `time_msc_utc` | `getTimeMscUtc()` | `int64` | time_msc in UTC (the same moment; time_msc is in the broker server's time zone). |
| `symbol` | `getSymbol()` | `string` |  |

## OnSymbolTickData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_tick` | `getSymbolTick()` | [OnSymbolMqlTickInfo](messages.md#onsymbolmqltickinfo) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnTadeEventData

| Field | Java | Type | Description |
|---|---|---|---|
| `new_orders` | `getNewOrdersList()` | repeated [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |
| `updated_orders` | `getUpdatedOrdersList()` | repeated [OnTradeUpdatedOrderInfo](messages.md#ontradeupdatedorderinfo) |  |
| `removed_orders` | `getRemovedOrdersList()` | repeated [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |
| `new_history_orders` | `getNewHistoryOrdersList()` | repeated [OnTradeOrderInfo](messages.md#ontradeorderinfo) | repeated OnTradeUpdatedOrderInfo updated_history_orders = 5; repeated OnTradeOrderInfo removed_history_orders = 6; |

## OnTradeData

| Field | Java | Type | Description |
|---|---|---|---|
| `type` | `getType()` | [MT4_SUB_ENUM_EVENT_GROUP_TYPE](enums.md#mt4_sub_enum_event_group_type) |  |
| `event_data` | `getEventData()` | [OnTadeEventData](messages.md#ontadeeventdata) |  |
| `account_info` | `getAccountInfo()` | [OnEventAccountInfo](messages.md#oneventaccountinfo) |  |
| `terminal_instance_guid_id` | `getTerminalInstanceGuidId()` | `string` |  |

## OnTradeOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `ticket` | `getTicket()` | `int32` |  |
| `is_history` | `getIsHistory()` | `bool` |  |
| `comment` | `getComment()` | `string` |  |
| `commission` | `getCommission()` | `double` |  |
| `expiration` | `getExpiration()` | `google.protobuf.Timestamp` |  |
| `expiration_utc` | `getExpirationUtc()` | `google.protobuf.Timestamp` | expiration in UTC (the same moment; expiration is in the broker server's time zone). |
| `lots` | `getLots()` | `double` |  |
| `magic_number` | `getMagicNumber()` | `int32` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` |  |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `order_profit` | `getOrderProfit()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `type` | `getType()` | [SUB_ORDER_OPERATION_TYPE](enums.md#sub_order_operation_type) |  |
| `close_price` | `getClosePrice()` | `double` | Only for closed orders (history) |
| `close_time` | `getCloseTime()` | `google.protobuf.Timestamp` | Only for closed orders (history) |
| `close_time_utc` | `getCloseTimeUtc()` | `google.protobuf.Timestamp` | close_time in UTC (the same moment; close_time is in the broker server's time zone). |
| `account_login` | `getAccountLogin()` | `int64` |  |

## OnTradeUpdatedOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `previous` | `getPrevious()` | [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |
| `current` | `getCurrent()` | [OnTradeOrderInfo](messages.md#ontradeorderinfo) |  |

## OpenedOrderInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `comment` | `getComment()` | `string` |  |
| `commision` | `getCommision()` | `double` |  |
| `expiration_time` | `getExpirationTime()` | `google.protobuf.Timestamp` | Expiration of a pending order (broker server time). Unset when the order has no expiration. |
| `expiration_time_utc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | expiration_time in UTC (the same moment; expiration_time is in the broker server's time zone). |
| `lots` | `getLots()` | `double` |  |
| `magic_number` | `getMagicNumber()` | `int32` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `stop_loss` | `getStopLoss()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `take_profit` | `getTakeProfit()` | `double` |  |
| `ticket` | `getTicket()` | `int32` |  |
| `position_index` | `getPositionIndex()` | `int32` |  |
| `order_type` | `getOrderType()` | [OpenedOrderType](enums.md#openedordertype) |  |
| `sort_index` | `getSortIndex()` | `int32` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` | Open time of the order (broker server time). |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `account_login` | `getAccountLogin()` | `int64` |  |

## OpenedOrdersData

| Field | Java | Type | Description |
|---|---|---|---|
| `order_infos` | `getOrderInfosList()` | repeated [OpenedOrderInfo](messages.md#openedorderinfo) |  |

## OpenedOrdersTicketsData

| Field | Java | Type | Description |
|---|---|---|---|
| `tickets` | `getTicketsList()` | repeated `int32` |  |

## OrderCloseByData

| Field | Java | Type | Description |
|---|---|---|---|
| `close_price` | `getClosePrice()` | `double` |  |
| `profit` | `getProfit()` | `double` |  |
| `close_time` | `getCloseTime()` | `google.protobuf.Timestamp` | Close time of the orders (broker server time). With UTC time the call is refused before the orders are closed when the offset cannot be determined (ACCOUNT_NOT_CONNECTED / SERVER_TIMEZONE_UNDETERMINED). |
| `close_time_utc` | `getCloseTimeUtc()` | `google.protobuf.Timestamp` | close_time in UTC (the same moment; close_time is in the broker server's time zone). |

## OrderCloseDeleteData

| Field | Java | Type | Description |
|---|---|---|---|
| `mode` | `getMode()` | [OrderCloseDeleteMode](enums.md#orderclosedeletemode) |  |
| `history_order_comment` | `getHistoryOrderComment()` | `string` |  |

## OrderModifyData

| Field | Java | Type | Description |
|---|---|---|---|
| `order_was_modified` | `getOrderWasModified()` | `bool` |  |

## OrderSendData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `int32` |  |
| `volume` | `getVolume()` | `double` |  |
| `price` | `getPrice()` | `double` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` | Open time of the order (broker server time). With UTC time the call is refused before the order is sent when the offset cannot be determined (ACCOUNT_NOT_CONNECTED / SERVER_TIMEZONE_UNDETERMINED), so an executed order always has a reply in the same time zone as its request. |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |

## OrdersHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `orders_info` | `getOrdersInfoList()` | repeated [HistoryOrderInfo](messages.md#historyorderinfo) | Rows of this page (all rows when no paging field is set), in the order of the request's sort mode; rows with an equal sort key follow in ticket order, ascending in the ascending modes and descending in the descending modes. |
| `total_count` | `getTotalCount()` | `int32` | Number of orders in the whole selection, over all pages (at the moment of the call). |
| `next_page_token` | `getNextPageToken()` | `string` | Cursor paging: pass back as page_token for the next page; empty when this was the last page (end of history for the range). |

## ProfitData

| Field | Java | Type | Description |
|---|---|---|---|
| `tiket` | `getTiket()` | `int64` |  |
| `date` | `getDate()` | `google.protobuf.Timestamp` |  |
| `date_utc` | `getDateUtc()` | `google.protobuf.Timestamp` | date in UTC (the same moment; date is in the broker server's time zone). |
| `profit` | `getProfit()` | `double` |  |

## ProfitabilityData

| Field | Java | Type | Description |
|---|---|---|---|
| `won_trades` | `getWonTrades()` | `int64` |  |
| `won_trades_percent` | `getWonTradesPercent()` | `double` |  |
| `lost_trades` | `getLostTrades()` | `int64` |  |
| `lost_trades_percent` | `getLostTradesPercent()` | `double` |  |

## QuoteData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` |  |
| `bid` | `getBid()` | `double` |  |
| `ask` | `getAsk()` | `double` |  |
| `date_time` | `getDateTime()` | `google.protobuf.Timestamp` | Time of the quote (broker server time). Unset when the terminal has no quote time. |
| `date_time_utc` | `getDateTimeUtc()` | `google.protobuf.Timestamp` | date_time in UTC (the same moment; date_time is in the broker server's time zone). |
| `high` | `getHigh()` | `double` |  |
| `low` | `getLow()` | `double` |  |

## QuoteHistoryBar

One bar (candle), named as MQL4 names the fields of MqlRates.

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `int64` | Unix seconds (UTC), bar open |
| `time_utc` | `getTimeUtc()` | `int64` | time in UTC (the same moment; time is in the broker server's time zone). |
| `open` | `getOpen()` | `double` |  |
| `high` | `getHigh()` | `double` |  |
| `low` | `getLow()` | `double` |  |
| `close` | `getClose()` | `double` |  |
| `tick_volume` | `getTickVolume()` | `int64` | ticks in the bar |
| `spread` | `getSpread()` | `int32` | points |
| `real_volume` | `getRealVolume()` | `int64` | 0 on MT4 |

## QuoteHistoryBarsData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol` | `getSymbol()` | `string` |  |
| `timeframe` | `getTimeframe()` | [QUOTE_HISTORY_TIMEFRAME](enums.md#quote_history_timeframe) |  |
| `bars` | `getBarsList()` | repeated [QuoteHistoryBar](messages.md#quotehistorybar) | Oldest first. Empty when the range holds no bars. |
| `next_page_token` | `getNextPageToken()` | `string` | Pass back as page_token for the next page; empty when this was the last. |
| `server_first_time` | `getServerFirstTime()` | `int64` | Unix seconds of the oldest bar the terminal has for this symbol and timeframe (0 when unknown). |
| `server_first_time_utc` | `getServerFirstTimeUtc()` | `int64` | server_first_time in UTC (the same moment; server_first_time is in the broker server's time zone). |

## QuoteHistoryData

| Field | Java | Type | Description |
|---|---|---|---|
| `historical_quotes` | `getHistoricalQuotesList()` | repeated [HistoryQuote](messages.md#historyquote) |  |

## QuoteManyData

| Field | Java | Type | Description |
|---|---|---|---|
| `quotes` | `getQuotesList()` | repeated [QuoteData](messages.md#quotedata) |  |

## ReconnectData

| Field | Java | Type | Description |
|---|---|---|---|
| `terminal_was_recreated` | `getTerminalWasRecreated()` | `bool` |  |
| `terminal_type` | `getTerminalType()` | [TerminalType](enums.md#terminaltype) |  |
| `terminal_instance_guid` | `getTerminalInstanceGuid()` | `string` |  |
| `old_terminal_full_live_time_seconds` | `getOldTerminalFullLiveTimeSeconds()` | `int64` |  |
| `old_log_files` | `getOldLogFilesList()` | repeated [LogFileInfo](messages.md#logfileinfo) |  |

## ScreenshotData

| Field | Java | Type | Description |
|---|---|---|---|
| `image_data` | `getImageData()` | `bytes` |  |
| `display_number` | `getDisplayNumber()` | `int32` |  |
| `terminal_id` | `getTerminalId()` | `string` |  |
| `content_type` | `getContentType()` | `string` |  |

## ServerTimezoneData

| Field | Java | Type | Description |
|---|---|---|---|
| `server` | `getServer()` | `string` | The broker server name of the account. |
| `utc_offset_seconds` | `getUtcOffsetSeconds()` | `int32` | Seconds east of UTC (broker server time = UTC + utc_offset_seconds), a multiple of 15 minutes. Absent when not known (source SERVER_TIMEZONE_SOURCE_UNKNOWN); a present 0 is a UTC+0 broker. |
| `source` | `getSource()` | [SERVER_TIMEZONE_SOURCE](enums.md#server_timezone_source) | Where utc_offset_seconds came from. |
| `validated_utc` | `getValidatedUtc()` | `google.protobuf.Timestamp` | When the offset was last validated from a fresh quote: for LIVE the latest reading of this terminal (refreshed every few seconds), for LAST_VALIDATED the latest reading of any terminal of the same broker server. Unset when UNKNOWN. |

## StatsData

| Field | Java | Type | Description |
|---|---|---|---|
| `summary` | `getSummary()` | [TradeSummaryData](messages.md#tradesummarydata) |  |
| `max_balance_drawdown_raw` | `getMaxBalanceDrawdownRaw()` | `double` |  |
| `max_balance_drawdown_relative` | `getMaxBalanceDrawdownRelative()` | `double` |  |
| `max_equity_drawdown_raw` | `getMaxEquityDrawdownRaw()` | `double` |  |
| `max_equity_drawdown_relative` | `getMaxEquityDrawdownRelative()` | `double` |  |
| `markets` | `getMarketsList()` | repeated [MarketTradeCountData](messages.md#markettradecountdata) |  |
| `profitability` | `getProfitability()` | [ProfitabilityData](messages.md#profitabilitydata) |  |
| `pips` | `getPips()` | `double` |  |
| `lots` | `getLots()` | `double` |  |
| `comissions` | `getComissions()` | `double` |  |
| `average_win` | `getAverageWin()` | [AveragePipsUsdData](messages.md#averagepipsusddata) |  |
| `average_lost` | `getAverageLost()` | [AveragePipsUsdData](messages.md#averagepipsusddata) |  |
| `longs_won` | `getLongsWon()` | [WonData](messages.md#wondata) |  |
| `shorts_won` | `getShortsWon()` | [WonData](messages.md#wondata) |  |
| `best_trade` | `getBestTrade()` | [ProfitData](messages.md#profitdata) |  |
| `worst_trade` | `getWorstTrade()` | [ProfitData](messages.md#profitdata) |  |
| `best_trade_pips` | `getBestTradePips()` | [ProfitData](messages.md#profitdata) |  |
| `worst_trade_pips` | `getWorstTradePips()` | [ProfitData](messages.md#profitdata) |  |
| `average_trade_length` | `getAverageTradeLength()` | `string` |  |
| `profit_factor` | `getProfitFactor()` | `double` |  |
| `standard_deviation` | `getStandardDeviation()` | `double` |  |
| `sharpe_ratio` | `getSharpeRatio()` | `double` |  |
| `z_score` | `getZScore()` | [ZScoreData](messages.md#zscoredata) |  |
| `expectancy` | `getExpectancy()` | [ExpectancyData](messages.md#expectancydata) |  |
| `ghpr` | `getGhpr()` | `double` |  |
| `trades` | `getTrades()` | `int64` |  |

## StatsWithChartsData

| Field | Java | Type | Description |
|---|---|---|---|
| `stats` | `getStats()` | [StatsData](messages.md#statsdata) |  |
| `charts` | `getChartsList()` | repeated [EquityPointData](messages.md#equitypointdata) |  |
| `trades` | `getTradesList()` | repeated [TradeHistoryItemData](messages.md#tradehistoryitemdata) |  |
| `open_trades` | `getOpenTradesList()` | repeated [TradeHistoryItemData](messages.md#tradehistoryitemdata) |  |

## SymbolNameInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_name` | `getSymbolName()` | `string` |  |
| `symbol_index` | `getSymbolIndex()` | `int32` |  |

## SymbolParamsManyData

| Field | Java | Type | Description |
|---|---|---|---|
| `symbol_infos` | `getSymbolInfosList()` | repeated [SymbolParamsManyInfo](messages.md#symbolparamsmanyinfo) |  |

## SymbolParamsManyInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `is_selected` | `getIsSelected()` | `bool` |  |
| `visible` | `getVisible()` | `bool` |  |
| `session_deals` | `getSessionDeals()` | `int64` |  |
| `session_buy_orders` | `getSessionBuyOrders()` | `int64` |  |
| `session_sell_orders` | `getSessionSellOrders()` | `int64` |  |
| `volume` | `getVolume()` | `int64` |  |
| `volume_high` | `getVolumeHigh()` | `int64` |  |
| `VolumeLow` | `getVolumeLow()` | `int64` |  |
| `Time` | `getTime()` | `int64` | Time of the symbol's last quote, unix seconds (broker server time). 0 when there was none. |
| `TimeUtc` | `getTimeUtc()` | `int64` | Time in UTC (the same moment; Time is in the broker server's time zone). |
| `Digits` | `getDigits()` | `int32` |  |
| `SpreadFloat` | `getSpreadFloat()` | `bool` |  |
| `TicksBookDepth` | `getTicksBookDepth()` | `int32` |  |
| `TradeCalcMode` | `getTradeCalcMode()` | [SP_ENUM_TRADE_CALC_MODE](enums.md#sp_enum_trade_calc_mode) | MODE_MARGINCALCMODE Margin calculation mode. 0 - Forex; 1 - CFD; 2 - Futures; 3 - CFD for indices |
| `TradeMode` | `getTradeMode()` | [SP_ENUM_SYMBOL_TRADE_MODE](enums.md#sp_enum_symbol_trade_mode) |  |
| `StartTime` | `getStartTime()` | `google.protobuf.Timestamp` | Start of trading of the symbol (futures) (broker server time). Unset when the symbol has none. |
| `StartTimeUtc` | `getStartTimeUtc()` | `google.protobuf.Timestamp` | StartTime in UTC (the same moment; StartTime is in the broker server's time zone). |
| `ExpirationTime` | `getExpirationTime()` | `google.protobuf.Timestamp` | Expiration of the symbol (futures) (broker server time). Unset when the symbol has none. |
| `ExpirationTimeUtc` | `getExpirationTimeUtc()` | `google.protobuf.Timestamp` | ExpirationTime in UTC (the same moment; ExpirationTime is in the broker server's time zone). |
| `TradeStopsLevel` | `getTradeStopsLevel()` | `int32` |  |
| `TradeFreezeLevel` | `getTradeFreezeLevel()` | `int32` |  |
| `TradeExeMode` | `getTradeExeMode()` | [SP_ENUM_SYMBOL_TRADE_EXECUTION](enums.md#sp_enum_symbol_trade_execution) |  |
| `SwapMode` | `getSwapMode()` | `int32` |  |
| `SwapRollover3Days` | `getSwapRollover3Days()` | `int32` |  |
| `TheLast` | `getTheLast()` | `double` |  |
| `LastHigh` | `getLastHigh()` | `double` |  |
| `LastLow` | `getLastLow()` | `double` |  |
| `Point` | `getPoint()` | `double` |  |
| `TradeTickValue` | `getTradeTickValue()` | `double` |  |
| `TradeTickValueProfit` | `getTradeTickValueProfit()` | `double` |  |
| `TradeTickValueLoss` | `getTradeTickValueLoss()` | `double` |  |
| `TradeTickSize` | `getTradeTickSize()` | `double` |  |
| `TradeContractSize` | `getTradeContractSize()` | `double` |  |
| `VolumeMin` | `getVolumeMin()` | `double` |  |
| `VolumeMax` | `getVolumeMax()` | `double` |  |
| `VolumeStep` | `getVolumeStep()` | `double` |  |
| `VolumeLimit` | `getVolumeLimit()` | `double` |  |
| `SwapLong` | `getSwapLong()` | `double` |  |
| `SwapShort` | `getSwapShort()` | `double` |  |
| `MarginInitial` | `getMarginInitial()` | `double` |  |
| `MarginMaintenance` | `getMarginMaintenance()` | `double` |  |
| `MarginLong` | `getMarginLong()` | `double` |  |
| `MarginShort` | `getMarginShort()` | `double` |  |
| `MarginLimit` | `getMarginLimit()` | `double` |  |
| `MarginStop` | `getMarginStop()` | `double` |  |
| `MarginStopLimit` | `getMarginStopLimit()` | `double` |  |
| `SessionVolume` | `getSessionVolume()` | `double` |  |
| `SessionTurnover` | `getSessionTurnover()` | `double` |  |
| `SessionInterest` | `getSessionInterest()` | `double` |  |
| `SessionBuyOrdersVolume` | `getSessionBuyOrdersVolume()` | `double` |  |
| `SessionSellOrdersVolume` | `getSessionSellOrdersVolume()` | `double` |  |
| `SessionOpen` | `getSessionOpen()` | `double` |  |
| `SessionClose` | `getSessionClose()` | `double` |  |
| `SessionAW` | `getSessionAW()` | `double` |  |
| `SessionPriceSettlement` | `getSessionPriceSettlement()` | `double` |  |
| `SessionPriceLimitMin` | `getSessionPriceLimitMin()` | `double` |  |
| `SessionPriceLimitMax` | `getSessionPriceLimitMax()` | `double` |  |
| `Bid` | `getBid()` | `double` |  |
| `BidHigh` | `getBidHigh()` | `double` |  |
| `BidLow` | `getBidLow()` | `double` |  |
| `AskHigh` | `getAskHigh()` | `double` |  |
| `AskLow` | `getAskLow()` | `double` |  |
| `CurrencyBase` | `getCurrencyBase()` | `string` |  |
| `CurrencyProfit` | `getCurrencyProfit()` | `string` |  |
| `CurrencyMargin` | `getCurrencyMargin()` | `string` |  |
| `Bank` | `getBank()` | `string` |  |
| `SymDescription` | `getSymDescription()` | `string` |  |
| `Isin` | `getIsin()` | `string` |  |
| `Path` | `getPath()` | `string` |  |
| `SymbolName` | `getSymbolName()` | `string` |  |

## SymbolSelectData

| Field | Java | Type | Description |
|---|---|---|---|
| `success` | `getSuccess()` | `bool` | In case of failure returns false |

## SymbolTickValueWithSizeInfo

| Field | Java | Type | Description |
|---|---|---|---|
| `index` | `getIndex()` | `int32` |  |
| `symbolName` | `getSymbolName()` | `string` |  |
| `TradeTickValue` | `getTradeTickValue()` | `double` |  |
| `TradeTickSize` | `getTradeTickSize()` | `double` |  |
| `TradeContractSize` | `getTradeContractSize()` | `double` |  |

## SymbolsData

| Field | Java | Type | Description |
|---|---|---|---|
| `SymbolNameInfos` | `getSymbolNameInfosList()` | repeated [SymbolNameInfo](messages.md#symbolnameinfo) |  |

## TerminalHealthCheck

| Field | Java | Type | Description |
|---|---|---|---|
| `is_alive` | `getIsAlive()` | `bool` | Connection is alive |
| `api_is_alive` | `getApiIsAlive()` | `bool` |  |
| `terminal_is_connected_to_mt_server` | `getTerminalIsConnectedToMtServer()` | `bool` |  |

## TerminalJournalRow

| Field | Java | Type | Description |
|---|---|---|---|
| `time` | `getTime()` | `google.protobuf.Timestamp` | Timestamp of the log entry |
| `source` | `getSource()` | `string` | Source of the log entry (e.g. "Network", "MQL4", "Tester", EA name) |
| `message` | `getMessage()` | `string` | Log message text |

## TickValueWithSizeData

| Field | Java | Type | Description |
|---|---|---|---|
| `infos` | `getInfosList()` | repeated [SymbolTickValueWithSizeInfo](messages.md#symboltickvaluewithsizeinfo) |  |

## TradeHistoryItemData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `int64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `is_buy` | `getIsBuy()` | `bool` |  |
| `lots` | `getLots()` | `double` |  |
| `open_price` | `getOpenPrice()` | `double` |  |
| `close_price` | `getClosePrice()` | `double` |  |
| `open_time` | `getOpenTime()` | `google.protobuf.Timestamp` |  |
| `open_time_utc` | `getOpenTimeUtc()` | `google.protobuf.Timestamp` | open_time in UTC (the same moment; open_time is in the broker server's time zone). |
| `close_time` | `getCloseTime()` | `google.protobuf.Timestamp` |  |
| `close_time_utc` | `getCloseTimeUtc()` | `google.protobuf.Timestamp` | close_time in UTC (the same moment; close_time is in the broker server's time zone). |
| `profit` | `getProfit()` | `double` |  |
| `commission` | `getCommission()` | `double` |  |
| `swap` | `getSwap()` | `double` |  |
| `comment` | `getComment()` | `string` |  |

## TradeSummaryData

| Field | Java | Type | Description |
|---|---|---|---|
| `open_trades` | `getOpenTrades()` | `int32` |  |
| `open_profit` | `getOpenProfit()` | `double` |  |
| `day_profit` | `getDayProfit()` | `double` |  |
| `week_profit` | `getWeekProfit()` | `double` |  |
| `month_profit` | `getMonthProfit()` | `double` |  |
| `total_profit` | `getTotalProfit()` | `double` |  |

## TradeUnrealizedExtremaData

| Field | Java | Type | Description |
|---|---|---|---|
| `ticket` | `getTicket()` | `int64` |  |
| `symbol` | `getSymbol()` | `string` |  |
| `max_unrealized_time` | `getMaxUnrealizedTime()` | `google.protobuf.Timestamp` |  |
| `max_unrealized_time_utc` | `getMaxUnrealizedTimeUtc()` | `google.protobuf.Timestamp` | max_unrealized_time in UTC (the same moment; max_unrealized_time is in the broker server's time zone). |
| `max_unrealized_pl` | `getMaxUnrealizedPl()` | `double` |  |
| `min_unrealized_time` | `getMinUnrealizedTime()` | `google.protobuf.Timestamp` |  |
| `min_unrealized_time_utc` | `getMinUnrealizedTimeUtc()` | `google.protobuf.Timestamp` | min_unrealized_time in UTC (the same moment; min_unrealized_time is in the broker server's time zone). |
| `min_unrealized_pl` | `getMinUnrealizedPl()` | `double` |  |

## TradeUnrealizedPLData

| Field | Java | Type | Description |
|---|---|---|---|
| `extrema` | `getExtremaList()` | repeated [TradeUnrealizedExtremaData](messages.md#tradeunrealizedextremadata) |  |

## WonData

| Field | Java | Type | Description |
|---|---|---|---|
| `won_count` | `getWonCount()` | `int32` |  |
| `all` | `getAll()` | `int32` |  |
| `won_persent` | `getWonPersent()` | `double` |  |

## ZScoreData

| Field | Java | Type | Description |
|---|---|---|---|
| `z_score_decimal` | `getZScoreDecimal()` | `double` |  |
| `z_score_probability` | `getZScoreProbability()` | `double` |  |
