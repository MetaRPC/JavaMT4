package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================================
 * Quote history: the bars a chart is drawn from, for one symbol - returned by
 * PAGE, oldest first. MetaTrader 4 keeps no tick history, so there is no
 * Ticks call here; the MT5 API has one.
 * Bars walks a time range forward. A page carries at most page_size bars and,
 * when the range holds more, a next_page_token; passing that token back (same
 * symbol, timeframe and range) returns the next page. An empty next_page_token
 * means the range is exhausted. Tokens are opaque: a client stores and returns
 * them, nothing else.
 * History lives in the terminal and is fetched from the broker on demand. The
 * first request for a symbol or a period the terminal has not loaded yet can
 * take a few seconds, and when the broker has not delivered it within that
 * time the call answers with an error whose error_code is
 * QUOTE_HISTORY_NOT_READY - ask again a moment later.
 * Every call needs the terminal id header ("id"), like every other call.
 * ============================================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-quote-history.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QuoteHistoryGrpc {

  private QuoteHistoryGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.QuoteHistory";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest,
      mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply> getBarsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Bars",
      requestType = mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest.class,
      responseType = mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest,
      mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply> getBarsMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest, mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply> getBarsMethod;
    if ((getBarsMethod = QuoteHistoryGrpc.getBarsMethod) == null) {
      synchronized (QuoteHistoryGrpc.class) {
        if ((getBarsMethod = QuoteHistoryGrpc.getBarsMethod) == null) {
          QuoteHistoryGrpc.getBarsMethod = getBarsMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest, mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Bars"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply.getDefaultInstance()))
              .setSchemaDescriptor(new QuoteHistoryMethodDescriptorSupplier("Bars"))
              .build();
        }
      }
    }
    return getBarsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QuoteHistoryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryStub>() {
        @java.lang.Override
        public QuoteHistoryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteHistoryStub(channel, callOptions);
        }
      };
    return QuoteHistoryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QuoteHistoryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryBlockingStub>() {
        @java.lang.Override
        public QuoteHistoryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteHistoryBlockingStub(channel, callOptions);
        }
      };
    return QuoteHistoryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QuoteHistoryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryFutureStub>() {
        @java.lang.Override
        public QuoteHistoryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteHistoryFutureStub(channel, callOptions);
        }
      };
    return QuoteHistoryFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, for one symbol - returned by
   * PAGE, oldest first. MetaTrader 4 keeps no tick history, so there is no
   * Ticks call here; the MT5 API has one.
   * Bars walks a time range forward. A page carries at most page_size bars and,
   * when the range holds more, a next_page_token; passing that token back (same
   * symbol, timeframe and range) returns the next page. An empty next_page_token
   * means the range is exhausted. Tokens are opaque: a client stores and returns
   * them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static abstract class QuoteHistoryImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public void bars(mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBarsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getBarsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest,
                mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply>(
                  this, METHODID_BARS)))
          .build();
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, for one symbol - returned by
   * PAGE, oldest first. MetaTrader 4 keeps no tick history, so there is no
   * Ticks call here; the MT5 API has one.
   * Bars walks a time range forward. A page carries at most page_size bars and,
   * when the range holds more, a next_page_token; passing that token back (same
   * symbol, timeframe and range) returns the next page. An empty next_page_token
   * means the range is exhausted. Tokens are opaque: a client stores and returns
   * them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static final class QuoteHistoryStub extends io.grpc.stub.AbstractAsyncStub<QuoteHistoryStub> {
    private QuoteHistoryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteHistoryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteHistoryStub(channel, callOptions);
    }

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public void bars(mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBarsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, for one symbol - returned by
   * PAGE, oldest first. MetaTrader 4 keeps no tick history, so there is no
   * Ticks call here; the MT5 API has one.
   * Bars walks a time range forward. A page carries at most page_size bars and,
   * when the range holds more, a next_page_token; passing that token back (same
   * symbol, timeframe and range) returns the next page. An empty next_page_token
   * means the range is exhausted. Tokens are opaque: a client stores and returns
   * them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static final class QuoteHistoryBlockingStub extends io.grpc.stub.AbstractBlockingStub<QuoteHistoryBlockingStub> {
    private QuoteHistoryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteHistoryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteHistoryBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply bars(mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBarsMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, for one symbol - returned by
   * PAGE, oldest first. MetaTrader 4 keeps no tick history, so there is no
   * Ticks call here; the MT5 API has one.
   * Bars walks a time range forward. A page carries at most page_size bars and,
   * when the range holds more, a next_page_token; passing that token back (same
   * symbol, timeframe and range) returns the next page. An empty next_page_token
   * means the range is exhausted. Tokens are opaque: a client stores and returns
   * them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static final class QuoteHistoryFutureStub extends io.grpc.stub.AbstractFutureStub<QuoteHistoryFutureStub> {
    private QuoteHistoryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteHistoryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteHistoryFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply> bars(
        mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBarsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_BARS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QuoteHistoryImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QuoteHistoryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BARS:
          serviceImpl.bars((mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiQuoteHistory.QuoteHistoryBarsReply>) responseObserver);
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

  private static abstract class QuoteHistoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QuoteHistoryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiQuoteHistory.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QuoteHistory");
    }
  }

  private static final class QuoteHistoryFileDescriptorSupplier
      extends QuoteHistoryBaseDescriptorSupplier {
    QuoteHistoryFileDescriptorSupplier() {}
  }

  private static final class QuoteHistoryMethodDescriptorSupplier
      extends QuoteHistoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QuoteHistoryMethodDescriptorSupplier(String methodName) {
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
      synchronized (QuoteHistoryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QuoteHistoryFileDescriptorSupplier())
              .addMethod(getBarsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
