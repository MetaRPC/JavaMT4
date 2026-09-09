package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-market-info.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MarketInfoGrpc {

  private MarketInfoGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.MarketInfo";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest,
      mt4_term_api.Mt4TermApiMarketInfo.QuoteReply> getQuoteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Quote",
      requestType = mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest.class,
      responseType = mt4_term_api.Mt4TermApiMarketInfo.QuoteReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest,
      mt4_term_api.Mt4TermApiMarketInfo.QuoteReply> getQuoteMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest, mt4_term_api.Mt4TermApiMarketInfo.QuoteReply> getQuoteMethod;
    if ((getQuoteMethod = MarketInfoGrpc.getQuoteMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getQuoteMethod = MarketInfoGrpc.getQuoteMethod) == null) {
          MarketInfoGrpc.getQuoteMethod = getQuoteMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest, mt4_term_api.Mt4TermApiMarketInfo.QuoteReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Quote"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.QuoteReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("Quote"))
              .build();
        }
      }
    }
    return getQuoteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest,
      mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply> getQuoteManyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuoteMany",
      requestType = mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest.class,
      responseType = mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest,
      mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply> getQuoteManyMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest, mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply> getQuoteManyMethod;
    if ((getQuoteManyMethod = MarketInfoGrpc.getQuoteManyMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getQuoteManyMethod = MarketInfoGrpc.getQuoteManyMethod) == null) {
          MarketInfoGrpc.getQuoteManyMethod = getQuoteManyMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest, mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuoteMany"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("QuoteMany"))
              .build();
        }
      }
    }
    return getQuoteManyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest,
      mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply> getSymbolsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Symbols",
      requestType = mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest.class,
      responseType = mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest,
      mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply> getSymbolsMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest, mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply> getSymbolsMethod;
    if ((getSymbolsMethod = MarketInfoGrpc.getSymbolsMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolsMethod = MarketInfoGrpc.getSymbolsMethod) == null) {
          MarketInfoGrpc.getSymbolsMethod = getSymbolsMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest, mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Symbols"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("Symbols"))
              .build();
        }
      }
    }
    return getSymbolsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest,
      mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply> getQuoteHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuoteHistory",
      requestType = mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest.class,
      responseType = mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest,
      mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply> getQuoteHistoryMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest, mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply> getQuoteHistoryMethod;
    if ((getQuoteHistoryMethod = MarketInfoGrpc.getQuoteHistoryMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getQuoteHistoryMethod = MarketInfoGrpc.getQuoteHistoryMethod) == null) {
          MarketInfoGrpc.getQuoteHistoryMethod = getQuoteHistoryMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest, mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuoteHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("QuoteHistory"))
              .build();
        }
      }
    }
    return getQuoteHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest,
      mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply> getSymbolSelectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolSelect",
      requestType = mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest.class,
      responseType = mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest,
      mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply> getSymbolSelectMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest, mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply> getSymbolSelectMethod;
    if ((getSymbolSelectMethod = MarketInfoGrpc.getSymbolSelectMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolSelectMethod = MarketInfoGrpc.getSymbolSelectMethod) == null) {
          MarketInfoGrpc.getSymbolSelectMethod = getSymbolSelectMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest, mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolSelect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolSelect"))
              .build();
        }
      }
    }
    return getSymbolSelectMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MarketInfoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketInfoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketInfoStub>() {
        @java.lang.Override
        public MarketInfoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketInfoStub(channel, callOptions);
        }
      };
    return MarketInfoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MarketInfoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketInfoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketInfoBlockingStub>() {
        @java.lang.Override
        public MarketInfoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketInfoBlockingStub(channel, callOptions);
        }
      };
    return MarketInfoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MarketInfoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketInfoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketInfoFutureStub>() {
        @java.lang.Override
        public MarketInfoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketInfoFutureStub(channel, callOptions);
        }
      };
    return MarketInfoFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class MarketInfoImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Get quote
     * </pre>
     */
    public void quote(mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuoteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Latest quote for the specified symbol
     * </pre>
     */
    public void quoteMany(mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuoteManyMethod(), responseObserver);
    }

    /**
     */
    public void symbols(mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolsMethod(), responseObserver);
    }

    /**
     */
    public void quoteHistory(mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuoteHistoryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://docs.mql4.com/marketinformation/symbolselect
     * </pre>
     */
    public void symbolSelect(mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolSelectMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getQuoteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest,
                mt4_term_api.Mt4TermApiMarketInfo.QuoteReply>(
                  this, METHODID_QUOTE)))
          .addMethod(
            getQuoteManyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest,
                mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply>(
                  this, METHODID_QUOTE_MANY)))
          .addMethod(
            getSymbolsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest,
                mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply>(
                  this, METHODID_SYMBOLS)))
          .addMethod(
            getQuoteHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest,
                mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply>(
                  this, METHODID_QUOTE_HISTORY)))
          .addMethod(
            getSymbolSelectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest,
                mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply>(
                  this, METHODID_SYMBOL_SELECT)))
          .build();
    }
  }

  /**
   */
  public static final class MarketInfoStub extends io.grpc.stub.AbstractAsyncStub<MarketInfoStub> {
    private MarketInfoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketInfoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketInfoStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get quote
     * </pre>
     */
    public void quote(mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuoteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Latest quote for the specified symbol
     * </pre>
     */
    public void quoteMany(mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuoteManyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void symbols(mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void quoteHistory(mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuoteHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://docs.mql4.com/marketinformation/symbolselect
     * </pre>
     */
    public void symbolSelect(mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolSelectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MarketInfoBlockingStub extends io.grpc.stub.AbstractBlockingStub<MarketInfoBlockingStub> {
    private MarketInfoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketInfoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketInfoBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get quote
     * </pre>
     */
    public mt4_term_api.Mt4TermApiMarketInfo.QuoteReply quote(mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuoteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Latest quote for the specified symbol
     * </pre>
     */
    public mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply quoteMany(mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuoteManyMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply symbols(mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolsMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply quoteHistory(mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuoteHistoryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://docs.mql4.com/marketinformation/symbolselect
     * </pre>
     */
    public mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply symbolSelect(mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolSelectMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MarketInfoFutureStub extends io.grpc.stub.AbstractFutureStub<MarketInfoFutureStub> {
    private MarketInfoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketInfoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketInfoFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get quote
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiMarketInfo.QuoteReply> quote(
        mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuoteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Latest quote for the specified symbol
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply> quoteMany(
        mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuoteManyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply> symbols(
        mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply> quoteHistory(
        mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuoteHistoryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://docs.mql4.com/marketinformation/symbolselect
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply> symbolSelect(
        mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolSelectMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_QUOTE = 0;
  private static final int METHODID_QUOTE_MANY = 1;
  private static final int METHODID_SYMBOLS = 2;
  private static final int METHODID_QUOTE_HISTORY = 3;
  private static final int METHODID_SYMBOL_SELECT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MarketInfoImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MarketInfoImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUOTE:
          serviceImpl.quote((mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteReply>) responseObserver);
          break;
        case METHODID_QUOTE_MANY:
          serviceImpl.quoteMany((mt4_term_api.Mt4TermApiMarketInfo.QuoteManyRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteManyReply>) responseObserver);
          break;
        case METHODID_SYMBOLS:
          serviceImpl.symbols((mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.SymbolsReply>) responseObserver);
          break;
        case METHODID_QUOTE_HISTORY:
          serviceImpl.quoteHistory((mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryReply>) responseObserver);
          break;
        case METHODID_SYMBOL_SELECT:
          serviceImpl.symbolSelect((mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectReply>) responseObserver);
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

  private static abstract class MarketInfoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MarketInfoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiMarketInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MarketInfo");
    }
  }

  private static final class MarketInfoFileDescriptorSupplier
      extends MarketInfoBaseDescriptorSupplier {
    MarketInfoFileDescriptorSupplier() {}
  }

  private static final class MarketInfoMethodDescriptorSupplier
      extends MarketInfoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MarketInfoMethodDescriptorSupplier(String methodName) {
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
      synchronized (MarketInfoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MarketInfoFileDescriptorSupplier())
              .addMethod(getQuoteMethod())
              .addMethod(getQuoteManyMethod())
              .addMethod(getSymbolsMethod())
              .addMethod(getQuoteHistoryMethod())
              .addMethod(getSymbolSelectMethod())
              .build();
        }
      }
    }
    return result;
  }
}
