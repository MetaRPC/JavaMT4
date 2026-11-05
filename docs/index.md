---
hide:
  - navigation
  - toc
---

# JavaMT4 SDK Documentation

> ℹ️ **Terminal session id**: when you connect via `connectByServerName` / `connectByHostPort`, the MRPC gateway assigns a terminal session id and returns it (also available as `account.getId()`). It is sent automatically with every later call and stream — you don't supply an `id` yourself before connecting.

<div class="hx-wrap" markdown="0">

<div class="hx-hero">
  <p class="hx-sub">Complete Java SDK for MetaTrader 4 trading automation via gRPC — connect, stream live quotes, and execute orders from any Java application.</p>
  <div class="hx-stats">
    <div class="hx-stat"><span class="hx-statV">3</span><span class="hx-statL">API Layers</span></div>
    <div class="hx-stat"><span class="hx-statV">gRPC</span><span class="hx-statL">Protocol</span></div>
    <div class="hx-stat"><span class="hx-statV">MT4</span><span class="hx-statL">Cloud Terminal</span></div>
    <div class="hx-stat"><span class="hx-statV">Java 17+</span><span class="hx-statL">Runtime</span></div>
  </div>
</div>

<div class="hx-cards hx-cards-4">

  <a href="All_Guides/Your_First_Project/" class="hx-gc hx-orange">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Get Started</div>
      <div class="hx-gcTitle">Quick Start</div>
      <p class="hx-gcDesc">Your first project from scratch in 10 minutes. Connect, read your balance, place an order.</p>
      <div class="hx-gcCount">10 min · hands-on</div>
    </div>
  </a>

  <a href="All_Guides/GETTING_STARTED/" class="hx-gc hx-blue">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Learn</div>
      <div class="hx-gcTitle">Getting Started</div>
      <p class="hx-gcDesc">New here? Start with setup, configuration and an overview of how the SDK is organized.</p>
      <div class="hx-gcCount">setup · overview</div>
    </div>
  </a>

  <a href="All_Guides/PROJECT_MAP/" class="hx-gc hx-purple">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Architecture</div>
      <div class="hx-gcTitle">Project Map</div>
      <p class="hx-gcDesc">Architecture overview — how low-level, mid-level and sugar layers fit together.</p>
      <div class="hx-gcCount">structure · layers</div>
    </div>
  </a>

  <a href="All_Guides/GLOSSARY/" class="hx-gc hx-pink">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Reference</div>
      <div class="hx-gcTitle">Glossary</div>
      <p class="hx-gcDesc">MT4 terms, return codes, enums and concepts — a reference while you build.</p>
      <div class="hx-gcCount">terms · concepts</div>
    </div>
  </a>

</div>

<div class="hx-cards">

  <a href="MT4Account/MT4Account.Master.Overview/" class="hx-gc hx-teal">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Reference · Low-level</div>
      <div class="hx-gcTitle">MT4Account</div>
      <p class="hx-gcDesc">Direct gRPC / protobuf layer. Maximum control over every request and response.</p>
      <div class="hx-gcCount">raw gRPC · full control</div>
    </div>
  </a>

  <a href="MT4Service/MT4Service.Overview/" class="hx-gc hx-rose">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Reference · Mid-level</div>
      <div class="hx-gcTitle">MT4Service</div>
      <p class="hx-gcDesc">Typed facade over MT4Account with friendly arguments and trade shortcuts.</p>
      <div class="hx-gcCount">30–50% less code</div>
    </div>
  </a>

  <a href="MT4Sugar/MT4Sugar.Master.Overview/" class="hx-gc hx-green">
    <div class="hx-gcTop"></div>
    <div class="hx-gcBody">
      <div class="hx-gcTag">Reference · High-level</div>
      <div class="hx-gcTitle">MT4Sugar</div>
      <p class="hx-gcDesc">One-liner operations, risk-based sizing and smart defaults for fast strategies.</p>
      <div class="hx-gcCount">one-liners · risk sizing</div>
    </div>
  </a>

</div>

</div>
