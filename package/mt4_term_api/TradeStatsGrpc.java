package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-trade-stats.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TradeStatsGrpc {

  private TradeStatsGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.TradeStats";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest,
      mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply> getTradeStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TradeStats",
      requestType = mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest,
      mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply> getTradeStatsMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest, mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply> getTradeStatsMethod;
    if ((getTradeStatsMethod = TradeStatsGrpc.getTradeStatsMethod) == null) {
      synchronized (TradeStatsGrpc.class) {
        if ((getTradeStatsMethod = TradeStatsGrpc.getTradeStatsMethod) == null) {
          TradeStatsGrpc.getTradeStatsMethod = getTradeStatsMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest, mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TradeStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeStatsMethodDescriptorSupplier("TradeStats"))
              .build();
        }
      }
    }
    return getTradeStatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest,
      mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply> getTradeStatsEquityHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TradeStatsEquityHistory",
      requestType = mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest,
      mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply> getTradeStatsEquityHistoryMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest, mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply> getTradeStatsEquityHistoryMethod;
    if ((getTradeStatsEquityHistoryMethod = TradeStatsGrpc.getTradeStatsEquityHistoryMethod) == null) {
      synchronized (TradeStatsGrpc.class) {
        if ((getTradeStatsEquityHistoryMethod = TradeStatsGrpc.getTradeStatsEquityHistoryMethod) == null) {
          TradeStatsGrpc.getTradeStatsEquityHistoryMethod = getTradeStatsEquityHistoryMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest, mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TradeStatsEquityHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeStatsMethodDescriptorSupplier("TradeStatsEquityHistory"))
              .build();
        }
      }
    }
    return getTradeStatsEquityHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest,
      mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply> getEquityHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EquityHistory",
      requestType = mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest,
      mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply> getEquityHistoryMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest, mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply> getEquityHistoryMethod;
    if ((getEquityHistoryMethod = TradeStatsGrpc.getEquityHistoryMethod) == null) {
      synchronized (TradeStatsGrpc.class) {
        if ((getEquityHistoryMethod = TradeStatsGrpc.getEquityHistoryMethod) == null) {
          TradeStatsGrpc.getEquityHistoryMethod = getEquityHistoryMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest, mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EquityHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeStatsMethodDescriptorSupplier("EquityHistory"))
              .build();
        }
      }
    }
    return getEquityHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest,
      mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply> getTradeUnrealizedPLMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TradeUnrealizedPL",
      requestType = mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest,
      mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply> getTradeUnrealizedPLMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest, mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply> getTradeUnrealizedPLMethod;
    if ((getTradeUnrealizedPLMethod = TradeStatsGrpc.getTradeUnrealizedPLMethod) == null) {
      synchronized (TradeStatsGrpc.class) {
        if ((getTradeUnrealizedPLMethod = TradeStatsGrpc.getTradeUnrealizedPLMethod) == null) {
          TradeStatsGrpc.getTradeUnrealizedPLMethod = getTradeUnrealizedPLMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest, mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TradeUnrealizedPL"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeStatsMethodDescriptorSupplier("TradeUnrealizedPL"))
              .build();
        }
      }
    }
    return getTradeUnrealizedPLMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TradeStatsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradeStatsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradeStatsStub>() {
        @java.lang.Override
        public TradeStatsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradeStatsStub(channel, callOptions);
        }
      };
    return TradeStatsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TradeStatsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradeStatsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradeStatsBlockingStub>() {
        @java.lang.Override
        public TradeStatsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradeStatsBlockingStub(channel, callOptions);
        }
      };
    return TradeStatsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TradeStatsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradeStatsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradeStatsFutureStub>() {
        @java.lang.Override
        public TradeStatsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradeStatsFutureStub(channel, callOptions);
        }
      };
    return TradeStatsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TradeStatsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Trading statistics calculated from order history and open orders
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public void tradeStats(mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTradeStatsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Trading statistics with equity history chart points
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public void tradeStatsEquityHistory(mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTradeStatsEquityHistoryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Equity history curve points with drawdown calculation
     * [DefaultValues]
     * {
     *   "from": "2024-10-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public void equityHistory(mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEquityHistoryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Maximum and minimum unrealized profit/loss per trade
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public void tradeUnrealizedPL(mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTradeUnrealizedPLMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTradeStatsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest,
                mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply>(
                  this, METHODID_TRADE_STATS)))
          .addMethod(
            getTradeStatsEquityHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest,
                mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply>(
                  this, METHODID_TRADE_STATS_EQUITY_HISTORY)))
          .addMethod(
            getEquityHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest,
                mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply>(
                  this, METHODID_EQUITY_HISTORY)))
          .addMethod(
            getTradeUnrealizedPLMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest,
                mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply>(
                  this, METHODID_TRADE_UNREALIZED_PL)))
          .build();
    }
  }

  /**
   */
  public static final class TradeStatsStub extends io.grpc.stub.AbstractAsyncStub<TradeStatsStub> {
    private TradeStatsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradeStatsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradeStatsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Trading statistics calculated from order history and open orders
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public void tradeStats(mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTradeStatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Trading statistics with equity history chart points
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public void tradeStatsEquityHistory(mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTradeStatsEquityHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Equity history curve points with drawdown calculation
     * [DefaultValues]
     * {
     *   "from": "2024-10-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public void equityHistory(mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEquityHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Maximum and minimum unrealized profit/loss per trade
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public void tradeUnrealizedPL(mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTradeUnrealizedPLMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TradeStatsBlockingStub extends io.grpc.stub.AbstractBlockingStub<TradeStatsBlockingStub> {
    private TradeStatsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradeStatsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradeStatsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Trading statistics calculated from order history and open orders
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply tradeStats(mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTradeStatsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Trading statistics with equity history chart points
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply tradeStatsEquityHistory(mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTradeStatsEquityHistoryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Equity history curve points with drawdown calculation
     * [DefaultValues]
     * {
     *   "from": "2024-10-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply equityHistory(mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEquityHistoryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Maximum and minimum unrealized profit/loss per trade
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply tradeUnrealizedPL(mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTradeUnrealizedPLMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TradeStatsFutureStub extends io.grpc.stub.AbstractFutureStub<TradeStatsFutureStub> {
    private TradeStatsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradeStatsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradeStatsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Trading statistics calculated from order history and open orders
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply> tradeStats(
        mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTradeStatsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Trading statistics with equity history chart points
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply> tradeStatsEquityHistory(
        mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTradeStatsEquityHistoryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Equity history curve points with drawdown calculation
     * [DefaultValues]
     * {
     *   "from": "2024-10-01T00:00:00",
     *   "excludeSameBars": true
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply> equityHistory(
        mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEquityHistoryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Maximum and minimum unrealized profit/loss per trade
     * [DefaultValues]
     * {
     *   "from": "2024-01-01T00:00:00"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply> tradeUnrealizedPL(
        mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTradeUnrealizedPLMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TRADE_STATS = 0;
  private static final int METHODID_TRADE_STATS_EQUITY_HISTORY = 1;
  private static final int METHODID_EQUITY_HISTORY = 2;
  private static final int METHODID_TRADE_UNREALIZED_PL = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TradeStatsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TradeStatsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TRADE_STATS:
          serviceImpl.tradeStats((mt4_term_api.Mt4TermApiTradeStats.TradeStatsRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeStatsReply>) responseObserver);
          break;
        case METHODID_TRADE_STATS_EQUITY_HISTORY:
          serviceImpl.tradeStatsEquityHistory((mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeStatsEquityHistoryReply>) responseObserver);
          break;
        case METHODID_EQUITY_HISTORY:
          serviceImpl.equityHistory((mt4_term_api.Mt4TermApiTradeStats.EquityHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.EquityHistoryReply>) responseObserver);
          break;
        case METHODID_TRADE_UNREALIZED_PL:
          serviceImpl.tradeUnrealizedPL((mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradeStats.TradeUnrealizedPLReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TradeStatsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TradeStatsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiTradeStats.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TradeStats");
    }
  }

  private static final class TradeStatsFileDescriptorSupplier
      extends TradeStatsBaseDescriptorSupplier {
    TradeStatsFileDescriptorSupplier() {}
  }

  private static final class TradeStatsMethodDescriptorSupplier
      extends TradeStatsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TradeStatsMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TradeStatsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TradeStatsFileDescriptorSupplier())
              .addMethod(getTradeStatsMethod())
              .addMethod(getTradeStatsEquityHistoryMethod())
              .addMethod(getEquityHistoryMethod())
              .addMethod(getTradeUnrealizedPLMethod())
              .build();
        }
      }
    }
    return result;
  }
}
