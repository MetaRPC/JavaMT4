package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-trading-helper.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TradingHelperGrpc {

  private TradingHelperGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.TradingHelper";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply> getOrderSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderSend",
      requestType = mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply> getOrderSendMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply> getOrderSendMethod;
    if ((getOrderSendMethod = TradingHelperGrpc.getOrderSendMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderSendMethod = TradingHelperGrpc.getOrderSendMethod) == null) {
          TradingHelperGrpc.getOrderSendMethod = getOrderSendMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderSend"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderSend"))
              .build();
        }
      }
    }
    return getOrderSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply> getOrderModifyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderModify",
      requestType = mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply> getOrderModifyMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply> getOrderModifyMethod;
    if ((getOrderModifyMethod = TradingHelperGrpc.getOrderModifyMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderModifyMethod = TradingHelperGrpc.getOrderModifyMethod) == null) {
          TradingHelperGrpc.getOrderModifyMethod = getOrderModifyMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderModify"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderModify"))
              .build();
        }
      }
    }
    return getOrderModifyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply> getOrderCloseDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderCloseDelete",
      requestType = mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply> getOrderCloseDeleteMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply> getOrderCloseDeleteMethod;
    if ((getOrderCloseDeleteMethod = TradingHelperGrpc.getOrderCloseDeleteMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderCloseDeleteMethod = TradingHelperGrpc.getOrderCloseDeleteMethod) == null) {
          TradingHelperGrpc.getOrderCloseDeleteMethod = getOrderCloseDeleteMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderCloseDelete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderCloseDelete"))
              .build();
        }
      }
    }
    return getOrderCloseDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply> getOrderCloseByMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderCloseBy",
      requestType = mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest.class,
      responseType = mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest,
      mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply> getOrderCloseByMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply> getOrderCloseByMethod;
    if ((getOrderCloseByMethod = TradingHelperGrpc.getOrderCloseByMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderCloseByMethod = TradingHelperGrpc.getOrderCloseByMethod) == null) {
          TradingHelperGrpc.getOrderCloseByMethod = getOrderCloseByMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest, mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderCloseBy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderCloseBy"))
              .build();
        }
      }
    }
    return getOrderCloseByMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TradingHelperStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradingHelperStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradingHelperStub>() {
        @java.lang.Override
        public TradingHelperStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradingHelperStub(channel, callOptions);
        }
      };
    return TradingHelperStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TradingHelperBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradingHelperBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradingHelperBlockingStub>() {
        @java.lang.Override
        public TradingHelperBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradingHelperBlockingStub(channel, callOptions);
        }
      };
    return TradingHelperBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TradingHelperFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradingHelperFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradingHelperFutureStub>() {
        @java.lang.Override
        public TradingHelperFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradingHelperFutureStub(channel, callOptions);
        }
      };
    return TradingHelperFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TradingHelperImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public void orderSend(mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderSendMethod(), responseObserver);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public void orderModify(mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderModifyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public void orderCloseDelete(mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderCloseDeleteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Close market order with opposite market order
     * </pre>
     */
    public void orderCloseBy(mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderCloseByMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOrderSendMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest,
                mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply>(
                  this, METHODID_ORDER_SEND)))
          .addMethod(
            getOrderModifyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest,
                mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply>(
                  this, METHODID_ORDER_MODIFY)))
          .addMethod(
            getOrderCloseDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest,
                mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply>(
                  this, METHODID_ORDER_CLOSE_DELETE)))
          .addMethod(
            getOrderCloseByMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest,
                mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply>(
                  this, METHODID_ORDER_CLOSE_BY)))
          .build();
    }
  }

  /**
   */
  public static final class TradingHelperStub extends io.grpc.stub.AbstractAsyncStub<TradingHelperStub> {
    private TradingHelperStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradingHelperStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradingHelperStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public void orderSend(mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public void orderModify(mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderModifyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public void orderCloseDelete(mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderCloseDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Close market order with opposite market order
     * </pre>
     */
    public void orderCloseBy(mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderCloseByMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TradingHelperBlockingStub extends io.grpc.stub.AbstractBlockingStub<TradingHelperBlockingStub> {
    private TradingHelperBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradingHelperBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradingHelperBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply orderSend(mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderSendMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply orderModify(mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderModifyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply orderCloseDelete(mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderCloseDeleteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Close market order with opposite market order
     * </pre>
     */
    public mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply orderCloseBy(mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderCloseByMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TradingHelperFutureStub extends io.grpc.stub.AbstractFutureStub<TradingHelperFutureStub> {
    private TradingHelperFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradingHelperFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradingHelperFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply> orderSend(
        mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderSendMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply> orderModify(
        mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderModifyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply> orderCloseDelete(
        mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderCloseDeleteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Close market order with opposite market order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply> orderCloseBy(
        mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderCloseByMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ORDER_SEND = 0;
  private static final int METHODID_ORDER_MODIFY = 1;
  private static final int METHODID_ORDER_CLOSE_DELETE = 2;
  private static final int METHODID_ORDER_CLOSE_BY = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TradingHelperImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TradingHelperImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ORDER_SEND:
          serviceImpl.orderSend((mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderSendReply>) responseObserver);
          break;
        case METHODID_ORDER_MODIFY:
          serviceImpl.orderModify((mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderModifyReply>) responseObserver);
          break;
        case METHODID_ORDER_CLOSE_DELETE:
          serviceImpl.orderCloseDelete((mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteReply>) responseObserver);
          break;
        case METHODID_ORDER_CLOSE_BY:
          serviceImpl.orderCloseBy((mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiTradingHelper.OrderCloseByReply>) responseObserver);
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

  private static abstract class TradingHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TradingHelperBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiTradingHelper.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TradingHelper");
    }
  }

  private static final class TradingHelperFileDescriptorSupplier
      extends TradingHelperBaseDescriptorSupplier {
    TradingHelperFileDescriptorSupplier() {}
  }

  private static final class TradingHelperMethodDescriptorSupplier
      extends TradingHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TradingHelperMethodDescriptorSupplier(String methodName) {
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
      synchronized (TradingHelperGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TradingHelperFileDescriptorSupplier())
              .addMethod(getOrderSendMethod())
              .addMethod(getOrderModifyMethod())
              .addMethod(getOrderCloseDeleteMethod())
              .addMethod(getOrderCloseByMethod())
              .build();
        }
      }
    }
    return result;
  }
}
