# JavaMT4 SDK

> Official MetaTrader 4 Java SDK for enterprise trading automation and market data streaming via gRPC.

[![Docs](https://img.shields.io/badge/docs-JavaMT4-blue.svg)](https://metarpc.github.io/JavaMT4/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

📄 **Full Documentation**: [https://metarpc.github.io/JavaMT4/](https://metarpc.github.io/JavaMT4/)

---

## 📦 Installation

Gradle, from [JitPack](https://jitpack.io/#MetaRPC/JavaMT4) (built from this repository on request):

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.MetaRPC:JavaMT4:fa9acfb453'   // a commit of this repository; main-SNAPSHOT = the latest
}
```

Maven: add the repository `https://jitpack.io` and the dependency `com.github.MetaRPC` / `JavaMT4` / `fa9acfb453`.
The jar brings gRPC and the generated `mt4_term_api` stubs along; Java 17+. It is not on Maven Central.

Or build it yourself: `git clone https://github.com/MetaRPC/JavaMT4.git && cd JavaMT4 && ./gradlew publishToMavenLocal` installs
`pro.mrpc:mt4:1.0.0` into your local Maven repository (`mavenLocal()`).

The jar holds the gRPC stubs generated from the current MT4 protos (`mt4_term_api`, synced into `package/` on every release).

---

## 🏃 How to Run the Example

[`examples/Quickstart.java`](src/main/java/examples/Quickstart.java) connects through `mt4.mrpc.pro:443` with `MT4Account`,
reads the account and EURUSD, buys 0.01 lot and closes it, streams three ticks and disconnects.

```bash
git clone https://github.com/MetaRPC/JavaMT4.git
cd JavaMT4
export MRPC_API_KEY="<YOUR_API_KEY>" MT4_LOGIN="<login>" MT4_PASSWORD="<password>" MT4_SERVER="TeleTrade-Demo"
./gradlew run                        # Windows: .\gradlew.bat run (set the variables with $env:NAME="..." first)
```

No MT4 account yet? `curl -H "APIKey: <YOUR_API_KEY>" "https://mt4.mrpc.pro/DemoAccount/Open?server=TeleTrade-Demo"`
opens a demo account and returns its login and password.

`MT4Client` in this package is a different client: it speaks the MT4 server protocol straight to a broker's MT4 server,
without MRPC.

---

## 🔑 API Key & Authentication

Connecting to MetaRPC production endpoints (`mt4.mrpc.pro:443`) requires an API key:

1. **Sign Up**: Create an account for free at [https://mrpc.pro/signup](https://mrpc.pro/signup).
2. **Generate API Key**: In your MetaRPC Portal dashboard at [https://mrpc.pro/my](https://mrpc.pro/my), go to **API Keys** to generate and copy your personal API token.
3. **Configure Connection**: Pass your API key / token along with the server address (`mt4.mrpc.pro:443`) in your connection settings.

---


---

## 🆔 Automatic Account ID & Authentication

MetaRPC endpoints route calls using a terminal session identifier (`id`):
- When connecting via `Connect` / `ConnectEx`, the server automatically generates and returns a session GUID (`terminalInstanceGuid`).
- The SDK automatically captures this session ID and attaches both the `id` and `APIKey` headers on all subsequent calls (`AccountSummary`, `OrderSend`, streaming, etc.) — no manual `GetId` or `curl` calls required.
- Pass your API key to `new MT4Account(apiKey)` or set the `MRPC_API_KEY` environment variable.

## 🌐 Production Endpoints

| Environment | Host | Port | Protocol |
| :--- | :--- | :--- | :--- |
| **Production** | `mt4.mrpc.pro` | `443` | TLS / gRPC |
| **Direct API UI (Swagger)** | `https://mt4.mrpc.pro/apiui` | `443` | HTTPS |
| **Portal Dashboard** | `https://mrpc.pro/my` | `443` | HTTPS |
| **Registration / API Key** | `https://mrpc.pro/signup` | `443` | HTTPS |

---

## 📄 Documentation & Guides

Explore comprehensive documentation at [https://metarpc.github.io/JavaMT4/](https://metarpc.github.io/JavaMT4/):
- 🚀 **Quick Start & First Project**
- 🔑 **Authentication & API Keys**
- 📡 **Live Market Data & gRPC Streaming**
- 💼 **Account Management & Order Execution**
- 📊 **Return Codes & Error Handling Reference**
