package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-subscriptions.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SubscriptionServiceGrpc {

  private SubscriptionServiceGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.SubscriptionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply> getOnTradeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnTrade",
      requestType = mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest.class,
      responseType = mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply> getOnTradeMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest, mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply> getOnTradeMethod;
    if ((getOnTradeMethod = SubscriptionServiceGrpc.getOnTradeMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnTradeMethod = SubscriptionServiceGrpc.getOnTradeMethod) == null) {
          SubscriptionServiceGrpc.getOnTradeMethod = getOnTradeMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest, mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnTrade"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnTrade"))
              .build();
        }
      }
    }
    return getOnTradeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply> getOnOpenedOrdersTicketsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnOpenedOrdersTickets",
      requestType = mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest.class,
      responseType = mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply> getOnOpenedOrdersTicketsMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest, mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply> getOnOpenedOrdersTicketsMethod;
    if ((getOnOpenedOrdersTicketsMethod = SubscriptionServiceGrpc.getOnOpenedOrdersTicketsMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnOpenedOrdersTicketsMethod = SubscriptionServiceGrpc.getOnOpenedOrdersTicketsMethod) == null) {
          SubscriptionServiceGrpc.getOnOpenedOrdersTicketsMethod = getOnOpenedOrdersTicketsMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest, mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnOpenedOrdersTickets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnOpenedOrdersTickets"))
              .build();
        }
      }
    }
    return getOnOpenedOrdersTicketsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply> getOnOpenedOrdersProfitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnOpenedOrdersProfit",
      requestType = mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest.class,
      responseType = mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply> getOnOpenedOrdersProfitMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest, mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply> getOnOpenedOrdersProfitMethod;
    if ((getOnOpenedOrdersProfitMethod = SubscriptionServiceGrpc.getOnOpenedOrdersProfitMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnOpenedOrdersProfitMethod = SubscriptionServiceGrpc.getOnOpenedOrdersProfitMethod) == null) {
          SubscriptionServiceGrpc.getOnOpenedOrdersProfitMethod = getOnOpenedOrdersProfitMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest, mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnOpenedOrdersProfit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnOpenedOrdersProfit"))
              .build();
        }
      }
    }
    return getOnOpenedOrdersProfitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply> getOnSymbolTickMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnSymbolTick",
      requestType = mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest.class,
      responseType = mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest,
      mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply> getOnSymbolTickMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest, mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply> getOnSymbolTickMethod;
    if ((getOnSymbolTickMethod = SubscriptionServiceGrpc.getOnSymbolTickMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnSymbolTickMethod = SubscriptionServiceGrpc.getOnSymbolTickMethod) == null) {
          SubscriptionServiceGrpc.getOnSymbolTickMethod = getOnSymbolTickMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest, mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnSymbolTick"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnSymbolTick"))
              .build();
        }
      }
    }
    return getOnSymbolTickMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SubscriptionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceStub>() {
        @java.lang.Override
        public SubscriptionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubscriptionServiceStub(channel, callOptions);
        }
      };
    return SubscriptionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SubscriptionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceBlockingStub>() {
        @java.lang.Override
        public SubscriptionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubscriptionServiceBlockingStub(channel, callOptions);
        }
      };
    return SubscriptionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SubscriptionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceFutureStub>() {
        @java.lang.Override
        public SubscriptionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubscriptionServiceFutureStub(channel, callOptions);
        }
      };
    return SubscriptionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SubscriptionServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Streams real-time order/trade events.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onTrade(mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnTradeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams the tickets of currently opened orders as they change.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onOpenedOrdersTickets(mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnOpenedOrdersTicketsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams the live profit of currently opened orders.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onOpenedOrdersProfit(mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnOpenedOrdersProfitMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams real-time ticks for the given symbols.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "symbolNames": "EURUSD,BTCUSD"
     * }
     * </pre>
     */
    public void onSymbolTick(mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnSymbolTickMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOnTradeMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest,
                mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply>(
                  this, METHODID_ON_TRADE)))
          .addMethod(
            getOnOpenedOrdersTicketsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest,
                mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply>(
                  this, METHODID_ON_OPENED_ORDERS_TICKETS)))
          .addMethod(
            getOnOpenedOrdersProfitMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest,
                mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply>(
                  this, METHODID_ON_OPENED_ORDERS_PROFIT)))
          .addMethod(
            getOnSymbolTickMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest,
                mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply>(
                  this, METHODID_ON_SYMBOL_TICK)))
          .build();
    }
  }

  /**
   */
  public static final class SubscriptionServiceStub extends io.grpc.stub.AbstractAsyncStub<SubscriptionServiceStub> {
    private SubscriptionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubscriptionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Streams real-time order/trade events.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onTrade(mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnTradeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams the tickets of currently opened orders as they change.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onOpenedOrdersTickets(mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnOpenedOrdersTicketsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams the live profit of currently opened orders.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onOpenedOrdersProfit(mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnOpenedOrdersProfitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams real-time ticks for the given symbols.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "symbolNames": "EURUSD,BTCUSD"
     * }
     * </pre>
     */
    public void onSymbolTick(mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnSymbolTickMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SubscriptionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SubscriptionServiceBlockingStub> {
    private SubscriptionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubscriptionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Streams real-time order/trade events.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply> onTrade(
        mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnTradeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams the tickets of currently opened orders as they change.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply> onOpenedOrdersTickets(
        mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnOpenedOrdersTicketsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams the live profit of currently opened orders.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply> onOpenedOrdersProfit(
        mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnOpenedOrdersProfitMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams real-time ticks for the given symbols.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "symbolNames": "EURUSD,BTCUSD"
     * }
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply> onSymbolTick(
        mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnSymbolTickMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SubscriptionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SubscriptionServiceFutureStub> {
    private SubscriptionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubscriptionServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_ON_TRADE = 0;
  private static final int METHODID_ON_OPENED_ORDERS_TICKETS = 1;
  private static final int METHODID_ON_OPENED_ORDERS_PROFIT = 2;
  private static final int METHODID_ON_SYMBOL_TICK = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SubscriptionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SubscriptionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ON_TRADE:
          serviceImpl.onTrade((mt4_term_api.Mt4TermApiSubscriptions.OnTradeRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnTradeReply>) responseObserver);
          break;
        case METHODID_ON_OPENED_ORDERS_TICKETS:
          serviceImpl.onOpenedOrdersTickets((mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersTicketsReply>) responseObserver);
          break;
        case METHODID_ON_OPENED_ORDERS_PROFIT:
          serviceImpl.onOpenedOrdersProfit((mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply>) responseObserver);
          break;
        case METHODID_ON_SYMBOL_TICK:
          serviceImpl.onSymbolTick((mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply>) responseObserver);
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

  private static abstract class SubscriptionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SubscriptionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiSubscriptions.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SubscriptionService");
    }
  }

  private static final class SubscriptionServiceFileDescriptorSupplier
      extends SubscriptionServiceBaseDescriptorSupplier {
    SubscriptionServiceFileDescriptorSupplier() {}
  }

  private static final class SubscriptionServiceMethodDescriptorSupplier
      extends SubscriptionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SubscriptionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SubscriptionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SubscriptionServiceFileDescriptorSupplier())
              .addMethod(getOnTradeMethod())
              .addMethod(getOnOpenedOrdersTicketsMethod())
              .addMethod(getOnOpenedOrdersProfitMethod())
              .addMethod(getOnSymbolTickMethod())
              .build();
        }
      }
    }
    return result;
  }
}
