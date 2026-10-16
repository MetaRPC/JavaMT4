# Enums

The enums of the [API methods](methods.md) (package `mt4_term_api`), generated from the protos. JSON (REST) uses the
value names; gRPC the numbers.

## ENUM_QUOTE_HISTORY_TIMEFRAME

| Value | Number | Description |
|---|---|---|
| `QH_PERIOD_M1` | 0 | 1 minute |
| `QH_PERIOD_M5` | 1 | 5 minutes |
| `QH_PERIOD_M15` | 2 | 15 minutes |
| `QH_PERIOD_M30` | 3 | 30 minutes |
| `QH_PERIOD_H1` | 4 | 1 hour |
| `QH_PERIOD_H4` | 5 | 4 hours |
| `QH_PERIOD_D1` | 6 | 1 day |
| `QH_PERIOD_W1` | 7 | 1 week |
| `QH_PERIOD_MN1` | 8 | 1 month |

## EnumAccountTradeMode

| Value | Number | Description |
|---|---|---|
| `ACCOUNT_TRADE_MODE_DEMO` | 0 |  |
| `ACCOUNT_TRADE_MODE_CONTEST` | 1 |  |
| `ACCOUNT_TRADE_MODE_REAL` | 2 |  |

## EnumOpenedOrderSortType

| Value | Number | Description |
|---|---|---|
| `SORT_BY_OPEN_TIME_ASC` | 0 |  |
| `SORT_BY_OPEN_TIME_DESC` | 1 |  |
| `SORT_BY_ORDER_TICKET_ID_ASC` | 2 |  |
| `SORT_BY_ORDER_TICKET_ID_DESC` | 3 |  |

## EnumOrderHistorySortType

| Value | Number | Description |
|---|---|---|
| `HISTORY_SORT_BY_OPEN_TIME_ASC` | 0 |  |
| `HISTORY_SORT_BY_OPEN_TIME_DESC` | 1 |  |
| `HISTORY_SORT_BY_CLOSE_TIME_ASC` | 2 |  |
| `HISTORY_SORT_BY_CLOSE_TIME_DESC` | 3 |  |
| `HISTORY_SORT_BY_ORDER_TICKET_ID_ASC` | 4 |  |
| `HISTORY_SORT_BY_ORDER_TICKET_ID_DESC` | 5 |  |

## ErrorType

| Value | Number | Description |
|---|---|---|
| `UNDEFINED` | 0 |  |
| `MRPC` | 1 |  |
| `MRPC_VALIDATION` | 2 |  |
| `MRPC_TIMEOUT` | 3 |  |
| `TERMINAL_API_TIMEOUT` | 4 |  |
| `TERMINAL_API_GET_PARAMS` | 5 |  |
| `TERMINAL_API_SET_RESULT` | 6 |  |
| `TERMINAL_API_UNEXPECTED` | 7 |  |
| `MQL_EXECUTION` | 8 |  |
| `MQL_CUSTOM_EXECUTION` | 9 |  |

## LogType

| Value | Number | Description |
|---|---|---|
| `Global` | 0 |  |
| `MQL5` | 1 |  |
| `MQL4` | 2 |  |

## MT4_SUB_ENUM_EVENT_GROUP_TYPE

| Value | Number | Description |
|---|---|---|
| `OrderProfit` | 0 |  |
| `OrderUpdate` | 1 |  |

## MqlErrorCode

| Value | Number | Description |
|---|---|---|
| `ERR_NO_ERROR` | 0 | No error |
| `ERR_NO_RESULT` | 1 | No result |
| `ERR_COMMON_ERROR` | 2 | Common error |
| `ERR_INVALID_TRADE_PARAMETERS` | 3 | Invalid trade parameters |
| `ERR_SERVER_BUSY` | 4 | Server busy |
| `ERR_OLD_VERSION` | 5 | Old version |
| `ERR_NO_CONNECTION` | 6 | No connection |
| `ERR_NOT_ENOUGH_RIGHTS` | 7 | Not enough rights |
| `ERR_TOO_FREQUENT_REQUESTS` | 8 | Too frequent requests |
| `ERR_MALFUNCTIONAL_TRADE` | 9 | Malfunctional trade |
| `ERR_ACCOUNT_DISABLED` | 64 | Account disabled |
| `ERR_INVALID_ACCOUNT` | 65 | Invalid account |
| `ERR_TRADE_TIMEOUT` | 128 | Trade timeout |
| `ERR_INVALID_PRICE` | 129 | Invalid price |
| `ERR_INVALID_STOPS` | 130 | Invalid stops |
| `ERR_INVALID_TRADE_VOLUME` | 131 | Invalid trade volume |
| `ERR_MARKET_CLOSED` | 132 | Market closed |
| `ERR_TRADE_DISABLED` | 133 | Trade disabled |
| `ERR_NOT_ENOUGH_MONEY` | 134 | Not enough money |
| `ERR_PRICE_CHANGED` | 135 | Price changed |
| `ERR_OFF_QUOTES` | 136 | Off quotes |
| `ERR_BROKER_BUSY` | 137 | Broker busy |
| `ERR_REQUOTE` | 138 | Requote |
| `ERR_ORDER_LOCKED` | 139 | Order locked |
| `ERR_LONG_POSITIONS_ONLY_ALLOWED` | 140 | Long positions only allowed |
| `ERR_TOO_MANY_REQUESTS` | 141 | Too many requests |
| `ERR_TRADE_MODIFY_DENIED` | 145 | Trade modify denied |
| `ERR_TRADE_CONTEXT_BUSY` | 146 | Trade context busy |
| `ERR_TRADE_EXPIRATION_DENIED` | 147 | Trade expiration denied |
| `ERR_TRADE_TOO_MANY_ORDERS` | 148 | Too many trade orders |
| `ERR_TRADE_HEDGE_PROHIBITED` | 149 | Trade hedge prohibited |
| `ERR_TRADE_PROHIBITED_BY_FIFO` | 150 | Trade prohibited by FIFO |
| `ERR_NO_MQLERROR` | 4000 | No MQL error |
| `ERR_WRONG_FUNCTION_POINTER` | 4001 | Wrong function pointer |
| `ERR_ARRAY_INDEX_OUT_OF_RANGE` | 4002 | Array index out of range |
| `ERR_NO_MEMORY_FOR_CALL_STACK` | 4003 | No memory for call stack |
| `ERR_RECURSIVE_STACK_OVERFLOW` | 4004 | Recursive stack overflow |
| `ERR_NOT_ENOUGH_STACK_FOR_PARAM` | 4005 | Not enough stack for parameter |
| `ERR_NO_MEMORY_FOR_PARAM_STRING` | 4006 | No memory for parameter string |
| `ERR_NO_MEMORY_FOR_TEMP_STRING` | 4007 | No memory for temp string |
| `ERR_NOT_INITIALIZED_STRING` | 4008 | Not initialized string |
| `ERR_NOT_INITIALIZED_ARRAYSTRING` | 4009 | Not initialized array string |
| `ERR_NO_MEMORY_FOR_ARRAYSTRING` | 4010 | No memory for array string |
| `ERR_TOO_LONG_STRING` | 4011 | Too long string |
| `ERR_REMAINDER_FROM_ZERO_DIVIDE` | 4012 | Remainder from zero divide |
| `ERR_ZERO_DIVIDE` | 4013 | Zero divide |
| `ERR_UNKNOWN_COMMAND` | 4014 | Unknown command |
| `ERR_WRONG_JUMP` | 4015 | Wrong jump |
| `ERR_NOT_INITIALIZED_ARRAY` | 4016 | Not initialized array |
| `ERR_DLL_CALLS_NOT_ALLOWED` | 4017 | DLL calls not allowed |
| `ERR_CANNOT_LOAD_LIBRARY` | 4018 | Cannot load library |
| `ERR_CANNOT_CALL_FUNCTION` | 4019 | Cannot call function |
| `ERR_EXTERNAL_CALLS_NOT_ALLOWED` | 4020 | External calls not allowed |
| `ERR_NO_MEMORY_FOR_RETURNED_STR` | 4021 | No memory for returned string |
| `ERR_SYSTEM_BUSY` | 4022 | System busy |
| `ERR_DLLFUNC_CRITICALERROR` | 4023 | DLL function critical error |
| `ERR_INTERNAL_ERROR` | 4024 | Internal error |
| `ERR_OUT_OF_MEMORY` | 4025 | Out of memory |
| `ERR_INVALID_POINTER` | 4026 | Invalid pointer |
| `ERR_FORMAT_TOO_MANY_FORMATTERS` | 4027 | Too many formatters |
| `ERR_FORMAT_TOO_MANY_PARAMETERS` | 4028 | Too many parameters |
| `ERR_ARRAY_INVALID` | 4029 | Invalid array |
| `ERR_CHART_NOREPLY` | 4030 | Chart no reply |
| `ERR_INVALID_FUNCTION_PARAMSCNT` | 4050 | Invalid function parameters count |
| `ERR_INVALID_FUNCTION_PARAMVALUE` | 4051 | Invalid function parameter value |
| `ERR_STRING_FUNCTION_INTERNAL` | 4052 | String function internal error |
| `ERR_SOME_ARRAY_ERROR` | 4053 | Some array error |
| `ERR_INCORRECT_SERIESARRAY_USING` | 4054 | Incorrect series array usage |
| `ERR_CUSTOM_INDICATOR_ERROR` | 4055 | Custom indicator error |
| `ERR_INCOMPATIBLE_ARRAYS` | 4056 | Incompatible arrays |
| `ERR_GLOBAL_VARIABLES_PROCESSING` | 4057 | Global variables processing error |
| `ERR_GLOBAL_VARIABLE_NOT_FOUND` | 4058 | Global variable not found |
| `ERR_FUNC_NOT_ALLOWED_IN_TESTING` | 4059 | Function not allowed in testing |
| `ERR_FUNCTION_NOT_CONFIRMED` | 4060 | Function not confirmed |
| `ERR_SEND_MAIL_ERROR` | 4061 | Send mail error |
| `ERR_STRING_PARAMETER_EXPECTED` | 4062 | String parameter expected |
| `ERR_INTEGER_PARAMETER_EXPECTED` | 4063 | Integer parameter expected |
| `ERR_DOUBLE_PARAMETER_EXPECTED` | 4064 | Double parameter expected |
| `ERR_ARRAY_AS_PARAMETER_EXPECTED` | 4065 | Array as parameter expected |
| `ERR_HISTORY_WILL_UPDATED` | 4066 | History will be updated |
| `ERR_TRADE_ERROR` | 4067 | Trade error |
| `ERR_RESOURCE_NOT_FOUND` | 4068 | Resource not found |
| `ERR_RESOURCE_NOT_SUPPORTED` | 4069 | Resource not supported |
| `ERR_RESOURCE_DUPLICATED` | 4070 | Resource duplicated |
| `ERR_INDICATOR_CANNOT_INIT` | 4071 | Indicator cannot initialize |
| `ERR_INDICATOR_CANNOT_LOAD` | 4072 | Indicator cannot load |
| `ERR_NO_HISTORY_DATA` | 4073 | No history data |
| `ERR_NO_MEMORY_FOR_HISTORY` | 4074 | No memory for history |
| `ERR_NO_MEMORY_FOR_INDICATOR` | 4075 | No memory for indicator |
| `ERR_END_OF_FILE` | 4099 | End of file |
| `ERR_SOME_FILE_ERROR` | 4100 | Some file error |
| `ERR_WRONG_FILE_NAME` | 4101 | Wrong file name |
| `ERR_TOO_MANY_OPENED_FILES` | 4102 | Too many opened files |
| `ERR_CANNOT_OPEN_FILE` | 4103 | Cannot open file |
| `ERR_INCOMPATIBLE_FILEACCESS` | 4104 | Incompatible file access |
| `ERR_NO_ORDER_SELECTED` | 4105 | No order selected |
| `ERR_UNKNOWN_SYMBOL` | 4106 | Unknown symbol |
| `ERR_INVALID_PRICE_PARAM` | 4107 | Invalid price parameter |
| `ERR_INVALID_TICKET` | 4108 | Invalid ticket |
| `ERR_TRADE_NOT_ALLOWED` | 4109 | Trade not allowed |
| `ERR_LONGS_NOT_ALLOWED` | 4110 | Longs not allowed |
| `ERR_SHORTS_NOT_ALLOWED` | 4111 | Shorts not allowed |
| `ERR_TRADE_EXPERT_DISABLED_BY_SERVER` | 4112 | Trade expert disabled by server |
| `ERR_OBJECT_ALREADY_EXISTS` | 4200 | Object already exists |
| `ERR_UNKNOWN_OBJECT_PROPERTY` | 4201 | Unknown object property |
| `ERR_OBJECT_DOES_NOT_EXIST` | 4202 | Object does not exist |
| `ERR_UNKNOWN_OBJECT_TYPE` | 4203 | Unknown object type |
| `ERR_NO_OBJECT_NAME` | 4204 | No object name |
| `ERR_OBJECT_COORDINATES_ERROR` | 4205 | Object coordinates error |
| `ERR_NO_SPECIFIED_SUBWINDOW` | 4206 | No specified subwindow |
| `ERR_SOME_OBJECT_ERROR` | 4207 | Some object error |
| `ERR_FILE_CANNOT_OPEN` | 5004 | Cannot open file |

## OpenedOrderType

| Value | Number | Description |
|---|---|---|
| `OO_OP_BUY` | 0 |  |
| `OO_OP_SELL` | 1 |  |
| `OO_OP_BUYLIMIT` | 2 |  |
| `OO_OP_SELLLIMIT` | 3 |  |
| `OO_OP_BUYSTOP` | 4 |  |
| `OO_OP_SELLSTOP` | 5 |  |

## OrderCloseDeleteMode

| Value | Number | Description |
|---|---|---|
| `OCD_MARKET_ORDER` | 0 |  |
| `OCD_PENDING_ORDER` | 1 |  |

## OrderSendOperationType

| Value | Number | Description |
|---|---|---|
| `OC_OP_BUY` | 0 | Buy operation |
| `OC_OP_SELL` | 1 | Sell operation |
| `OC_OP_BUYLIMIT` | 2 | Buy limit pending order |
| `OC_OP_SELLLIMIT` | 3 | Sell limit pending order |
| `OC_OP_BUYSTOP` | 4 | Buy stop pending order |
| `OC_OP_SELLSTOP` | 5 | Sell stop pending order |

## ProxyTypes

Specifies the type of proxy servers that an instance of the ProxySocket class can use.

| Value | Number | Description |
|---|---|---|
| `None` | 0 | No proxy server; the ProxySocket object behaves exactly like an ordinary Socket object. |
| `Https` | 1 | A HTTPS (CONNECT) proxy server. |
| `Socks4` | 2 | A SOCKS4[A] proxy server. |
| `Socks5` | 3 | A SOCKS5 proxy server. |

## QUOTE_HISTORY_TIMEFRAME

Timeframe of a bar. The value is the bar length in MINUTES - the same numbers MQL4's ENUM_TIMEFRAMES uses. These are the nine periods MT4 has. The names carry the prefix QHB_ (not QH_ as on MT5) because the older MarketInfo.QuoteHistory of MT4 already owns QH_PERIOD_M1..MN1 (ENUM_QUOTE_HISTORY_TIMEFRAME, other numbers) and enum value names are shared by the whole package.

| Value | Number | Description |
|---|---|---|
| `QHB_PERIOD_UNSPECIFIED` | 0 |  |
| `QHB_PERIOD_M1` | 1 |  |
| `QHB_PERIOD_M5` | 5 |  |
| `QHB_PERIOD_M15` | 15 |  |
| `QHB_PERIOD_M30` | 30 |  |
| `QHB_PERIOD_H1` | 60 |  |
| `QHB_PERIOD_H4` | 240 |  |
| `QHB_PERIOD_D1` | 1440 |  |
| `QHB_PERIOD_W1` | 10080 |  |
| `QHB_PERIOD_MN1` | 43200 |  |

## SERVER_TIMEZONE_SOURCE

| Value | Number | Description |
|---|---|---|
| `SERVER_TIMEZONE_SOURCE_UNKNOWN` | 0 | No terminal of this broker server has read the offset yet (MT4 with the market closed); the *_utc fields stay unset. |
| `SERVER_TIMEZONE_SOURCE_LIVE` | 1 | Read by this terminal itself. |
| `SERVER_TIMEZONE_SOURCE_LAST_VALIDATED` | 2 | The last offset validated for this broker server, used until this terminal reads its own (MT4 with the market closed). |

## SP_ENUM_SYMBOL_TRADE_EXECUTION

| Value | Number | Description |
|---|---|---|
| `SYMBOL_TRADE_EXECUTION_REQUEST` | 0 | Execution by request |
| `SYMBOL_TRADE_EXECUTION_INSTANT` | 1 | Instant execution |
| `SYMBOL_TRADE_EXECUTION_MARKET` | 2 | Market execution |
| `SYMBOL_TRADE_EXECUTION_EXCHANGE` | 3 | Exchange execution |

## SP_ENUM_SYMBOL_TRADE_MODE

| Value | Number | Description |
|---|---|---|
| `SYMBOL_TRADE_MODE_DISABLED` | 0 | Trade is disabled for the symbol |
| `SYMBOL_TRADE_MODE_LONGONLY` | 1 | Allowed only long positions |
| `SYMBOL_TRADE_MODE_SHORTONLY` | 2 | Allowed only short positions |
| `SYMBOL_TRADE_MODE_CLOSEONLY` | 3 | Allowed only position close operations |
| `SYMBOL_TRADE_MODE_FULL` | 4 | No trade restrictions |

## SP_ENUM_TRADE_CALC_MODE

| Value | Number | Description |
|---|---|---|
| `SYMBOL_TRADE_MARGINE_CALC_MODE_FOREX` | 0 | 0 - Forex; |
| `SYMBOL_TRADE_MARGINE_CALC_MODE_FUTURES` | 1 | 2 - Futures; |
| `SYMBOL_TRADE_MARGINE_CALC_MODE_CFD` | 2 | 3 - CFD for indices |

## SUB_ORDER_OPERATION_TYPE

| Value | Number | Description |
|---|---|---|
| `SUB_OP_BUY` | 0 |  |
| `SUB_OP_SELL` | 1 |  |
| `SUB_OP_BUYLIMIT` | 2 |  |
| `SUB_OP_SELLLIMIT` | 3 |  |
| `SUB_OP_BUYSTOP` | 4 |  |
| `SUB_OP_SELLSTOP` | 5 |  |

## TerminalType

| Value | Number | Description |
|---|---|---|
| `MT4` | 0 |  |
| `MT5` | 1 |  |
