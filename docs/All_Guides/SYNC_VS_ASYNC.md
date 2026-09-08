# Synchronous vs Asynchronous Methods - When to Use What (Java)

> Understanding execution models in JavaMT4: non-blocking streaming vs synchronous execution.

---

## 🎯 Quick Comparison

Java SDK exposes `CompletableFuture<T>` for asynchronous calls and Reactive Streams / Flow for real-time market data streaming.

| Aspect | Asynchronous Pattern | Synchronous Call |
|--------|----------------------|------------------|
| **Thread Blocking** | ❌ Non-blocking (high concurrency) | ✅ Blocks current thread |
| **Throughput** | ✅ Handles thousands of events/sec | ❌ Limited by thread pool |
| **Real-Time Data** | ✅ Perfect for tick & trade streams | ⚠️ Inefficient for streams |
| **Simplicity** | Requires async runtime awareness | Simple, linear execution |
| **Recommended for** | Automated bots, microservices, GUIs | CLI scripts, notebooks, one-offs |

---

## 🚀 When to Use Asynchronous Methods (Recommended)

### 1. Market Data Streaming
Market ticks arrive at microsecond intervals during peak sessions. Asynchronous handlers ensure zero tick drops without freezing your execution thread:

```
MT4Account account = new MT4Account(user, password, grpcServer);
account.connectByServerNameAsync(serverName, "EURUSD", 30).get();
AccountSummary summary = account.accountSummaryAsync().get();
System.out.println("Balance: " + summary.getAccountBalance());
```

### 2. High-Frequency Order Execution
When operating across multiple currency pairs simultaneously, asynchronous dispatch allows your bot to send orders concurrently rather than sequentially.

---

## 💡 Summary

Always prefer asynchronous paradigms for production bots, multi-symbol trading, and background services. Use synchronous wrappers for quick setup scripts, testing, or exploratory analysis.
