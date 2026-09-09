package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-account-helper.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AccountHelperGrpc {

  private AccountHelperGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.AccountHelper";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest,
      mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply> getAccountSummaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AccountSummary",
      requestType = mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest,
      mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply> getAccountSummaryMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest, mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply> getAccountSummaryMethod;
    if ((getAccountSummaryMethod = AccountHelperGrpc.getAccountSummaryMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getAccountSummaryMethod = AccountHelperGrpc.getAccountSummaryMethod) == null) {
          AccountHelperGrpc.getAccountSummaryMethod = getAccountSummaryMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest, mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AccountSummary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("AccountSummary"))
              .build();
        }
      }
    }
    return getAccountSummaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest,
      mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply> getOpenedOrdersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenedOrders",
      requestType = mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest,
      mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply> getOpenedOrdersMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest, mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply> getOpenedOrdersMethod;
    if ((getOpenedOrdersMethod = AccountHelperGrpc.getOpenedOrdersMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getOpenedOrdersMethod = AccountHelperGrpc.getOpenedOrdersMethod) == null) {
          AccountHelperGrpc.getOpenedOrdersMethod = getOpenedOrdersMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest, mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenedOrders"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("OpenedOrders"))
              .build();
        }
      }
    }
    return getOpenedOrdersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest,
      mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply> getOpenedOrdersTicketsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenedOrdersTickets",
      requestType = mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest,
      mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply> getOpenedOrdersTicketsMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest, mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply> getOpenedOrdersTicketsMethod;
    if ((getOpenedOrdersTicketsMethod = AccountHelperGrpc.getOpenedOrdersTicketsMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getOpenedOrdersTicketsMethod = AccountHelperGrpc.getOpenedOrdersTicketsMethod) == null) {
          AccountHelperGrpc.getOpenedOrdersTicketsMethod = getOpenedOrdersTicketsMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest, mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenedOrdersTickets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("OpenedOrdersTickets"))
              .build();
        }
      }
    }
    return getOpenedOrdersTicketsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest,
      mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply> getOrdersHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrdersHistory",
      requestType = mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest,
      mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply> getOrdersHistoryMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest, mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply> getOrdersHistoryMethod;
    if ((getOrdersHistoryMethod = AccountHelperGrpc.getOrdersHistoryMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getOrdersHistoryMethod = AccountHelperGrpc.getOrdersHistoryMethod) == null) {
          AccountHelperGrpc.getOrdersHistoryMethod = getOrdersHistoryMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest, mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrdersHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("OrdersHistory"))
              .build();
        }
      }
    }
    return getOrdersHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest,
      mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply> getSymbolParamsManyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolParamsMany",
      requestType = mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest,
      mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply> getSymbolParamsManyMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest, mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply> getSymbolParamsManyMethod;
    if ((getSymbolParamsManyMethod = AccountHelperGrpc.getSymbolParamsManyMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getSymbolParamsManyMethod = AccountHelperGrpc.getSymbolParamsManyMethod) == null) {
          AccountHelperGrpc.getSymbolParamsManyMethod = getSymbolParamsManyMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest, mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolParamsMany"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("SymbolParamsMany"))
              .build();
        }
      }
    }
    return getSymbolParamsManyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest,
      mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply> getTickValueWithSizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TickValueWithSize",
      requestType = mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest,
      mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply> getTickValueWithSizeMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest, mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply> getTickValueWithSizeMethod;
    if ((getTickValueWithSizeMethod = AccountHelperGrpc.getTickValueWithSizeMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getTickValueWithSizeMethod = AccountHelperGrpc.getTickValueWithSizeMethod) == null) {
          AccountHelperGrpc.getTickValueWithSizeMethod = getTickValueWithSizeMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest, mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TickValueWithSize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("TickValueWithSize"))
              .build();
        }
      }
    }
    return getTickValueWithSizeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountHelperStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountHelperStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountHelperStub>() {
        @java.lang.Override
        public AccountHelperStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountHelperStub(channel, callOptions);
        }
      };
    return AccountHelperStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountHelperBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountHelperBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountHelperBlockingStub>() {
        @java.lang.Override
        public AccountHelperBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountHelperBlockingStub(channel, callOptions);
        }
      };
    return AccountHelperBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountHelperFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountHelperFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountHelperFutureStub>() {
        @java.lang.Override
        public AccountHelperFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountHelperFutureStub(channel, callOptions);
        }
      };
    return AccountHelperFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AccountHelperImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public void accountSummary(mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAccountSummaryMethod(), responseObserver);
    }

    /**
     * <pre>
     * List of opened orders
     * </pre>
     */
    public void openedOrders(mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenedOrdersMethod(), responseObserver);
    }

    /**
     * <pre>
     * List of opened orders tickets
     * </pre>
     */
    public void openedOrdersTickets(mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenedOrdersTicketsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Orders history with pagination and open date filters
     * </pre>
     */
    public void ordersHistory(mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrdersHistoryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Full information about exact symbol, or all symbols
     * </pre>
     */
    public void symbolParamsMany(mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolParamsManyMethod(), responseObserver);
    }

    /**
     */
    public void tickValueWithSize(mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTickValueWithSizeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAccountSummaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest,
                mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply>(
                  this, METHODID_ACCOUNT_SUMMARY)))
          .addMethod(
            getOpenedOrdersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest,
                mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply>(
                  this, METHODID_OPENED_ORDERS)))
          .addMethod(
            getOpenedOrdersTicketsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest,
                mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply>(
                  this, METHODID_OPENED_ORDERS_TICKETS)))
          .addMethod(
            getOrdersHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest,
                mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply>(
                  this, METHODID_ORDERS_HISTORY)))
          .addMethod(
            getSymbolParamsManyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest,
                mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply>(
                  this, METHODID_SYMBOL_PARAMS_MANY)))
          .addMethod(
            getTickValueWithSizeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest,
                mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply>(
                  this, METHODID_TICK_VALUE_WITH_SIZE)))
          .build();
    }
  }

  /**
   */
  public static final class AccountHelperStub extends io.grpc.stub.AbstractAsyncStub<AccountHelperStub> {
    private AccountHelperStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountHelperStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountHelperStub(channel, callOptions);
    }

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public void accountSummary(mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAccountSummaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List of opened orders
     * </pre>
     */
    public void openedOrders(mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenedOrdersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List of opened orders tickets
     * </pre>
     */
    public void openedOrdersTickets(mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenedOrdersTicketsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Orders history with pagination and open date filters
     * </pre>
     */
    public void ordersHistory(mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrdersHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Full information about exact symbol, or all symbols
     * </pre>
     */
    public void symbolParamsMany(mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolParamsManyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tickValueWithSize(mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTickValueWithSizeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AccountHelperBlockingStub extends io.grpc.stub.AbstractBlockingStub<AccountHelperBlockingStub> {
    private AccountHelperBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountHelperBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountHelperBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply accountSummary(mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAccountSummaryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List of opened orders
     * </pre>
     */
    public mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply openedOrders(mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenedOrdersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List of opened orders tickets
     * </pre>
     */
    public mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply openedOrdersTickets(mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenedOrdersTicketsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Orders history with pagination and open date filters
     * </pre>
     */
    public mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply ordersHistory(mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrdersHistoryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Full information about exact symbol, or all symbols
     * </pre>
     */
    public mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply symbolParamsMany(mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolParamsManyMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply tickValueWithSize(mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTickValueWithSizeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AccountHelperFutureStub extends io.grpc.stub.AbstractFutureStub<AccountHelperFutureStub> {
    private AccountHelperFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountHelperFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountHelperFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply> accountSummary(
        mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAccountSummaryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List of opened orders
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply> openedOrders(
        mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenedOrdersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List of opened orders tickets
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply> openedOrdersTickets(
        mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenedOrdersTicketsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Orders history with pagination and open date filters
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply> ordersHistory(
        mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrdersHistoryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Full information about exact symbol, or all symbols
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply> symbolParamsMany(
        mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolParamsManyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply> tickValueWithSize(
        mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTickValueWithSizeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ACCOUNT_SUMMARY = 0;
  private static final int METHODID_OPENED_ORDERS = 1;
  private static final int METHODID_OPENED_ORDERS_TICKETS = 2;
  private static final int METHODID_ORDERS_HISTORY = 3;
  private static final int METHODID_SYMBOL_PARAMS_MANY = 4;
  private static final int METHODID_TICK_VALUE_WITH_SIZE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AccountHelperImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AccountHelperImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ACCOUNT_SUMMARY:
          serviceImpl.accountSummary((mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply>) responseObserver);
          break;
        case METHODID_OPENED_ORDERS:
          serviceImpl.openedOrders((mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersReply>) responseObserver);
          break;
        case METHODID_OPENED_ORDERS_TICKETS:
          serviceImpl.openedOrdersTickets((mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersTicketsReply>) responseObserver);
          break;
        case METHODID_ORDERS_HISTORY:
          serviceImpl.ordersHistory((mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryReply>) responseObserver);
          break;
        case METHODID_SYMBOL_PARAMS_MANY:
          serviceImpl.symbolParamsMany((mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyReply>) responseObserver);
          break;
        case METHODID_TICK_VALUE_WITH_SIZE:
          serviceImpl.tickValueWithSize((mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccountHelper.TickValueWithSizeReply>) responseObserver);
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

  private static abstract class AccountHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountHelperBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiAccountHelper.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccountHelper");
    }
  }

  private static final class AccountHelperFileDescriptorSupplier
      extends AccountHelperBaseDescriptorSupplier {
    AccountHelperFileDescriptorSupplier() {}
  }

  private static final class AccountHelperMethodDescriptorSupplier
      extends AccountHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AccountHelperMethodDescriptorSupplier(String methodName) {
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
      synchronized (AccountHelperGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountHelperFileDescriptorSupplier())
              .addMethod(getAccountSummaryMethod())
              .addMethod(getOpenedOrdersMethod())
              .addMethod(getOpenedOrdersTicketsMethod())
              .addMethod(getOrdersHistoryMethod())
              .addMethod(getSymbolParamsManyMethod())
              .addMethod(getTickValueWithSizeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
