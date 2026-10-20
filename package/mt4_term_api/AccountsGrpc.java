package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================================
 * Accounts: the MT4 accounts (terminals) that belong to your API key.
 * This is the call to make when you need the terminal ids of accounts that already exist - started
 * from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of
 * every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the
 * platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills
 * a terminal.
 * Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only
 * accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists
 * MT4 accounts).
 * ============================================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-accounts.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AccountsGrpc {

  private AccountsGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.Accounts";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccounts.AccountsListRequest,
      mt4_term_api.Mt4TermApiAccounts.AccountsListReply> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "List",
      requestType = mt4_term_api.Mt4TermApiAccounts.AccountsListRequest.class,
      responseType = mt4_term_api.Mt4TermApiAccounts.AccountsListReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccounts.AccountsListRequest,
      mt4_term_api.Mt4TermApiAccounts.AccountsListReply> getListMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiAccounts.AccountsListRequest, mt4_term_api.Mt4TermApiAccounts.AccountsListReply> getListMethod;
    if ((getListMethod = AccountsGrpc.getListMethod) == null) {
      synchronized (AccountsGrpc.class) {
        if ((getListMethod = AccountsGrpc.getListMethod) == null) {
          AccountsGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiAccounts.AccountsListRequest, mt4_term_api.Mt4TermApiAccounts.AccountsListReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "List"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccounts.AccountsListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiAccounts.AccountsListReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountsMethodDescriptorSupplier("List"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsStub>() {
        @java.lang.Override
        public AccountsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsStub(channel, callOptions);
        }
      };
    return AccountsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsBlockingStub>() {
        @java.lang.Override
        public AccountsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsBlockingStub(channel, callOptions);
        }
      };
    return AccountsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsFutureStub>() {
        @java.lang.Override
        public AccountsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsFutureStub(channel, callOptions);
        }
      };
    return AccountsFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================================
   * Accounts: the MT4 accounts (terminals) that belong to your API key.
   * This is the call to make when you need the terminal ids of accounts that already exist - started
   * from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of
   * every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the
   * platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills
   * a terminal.
   * Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only
   * accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists
   * MT4 accounts).
   * ============================================================================
   * </pre>
   */
  public static abstract class AccountsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Lists the accounts of the API key sent in the APIKey header, one page per call. Pass the
     * next_page_token of a page back as page_token to continue; an empty next_page_token means that was
     * the last page.
     * [DefaultValues]
     * {
     *   "includeStopped": "false",
     *   "pageSize": "200"
     * }
     * </pre>
     */
    public void list(mt4_term_api.Mt4TermApiAccounts.AccountsListRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccounts.AccountsListReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiAccounts.AccountsListRequest,
                mt4_term_api.Mt4TermApiAccounts.AccountsListReply>(
                  this, METHODID_LIST)))
          .build();
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Accounts: the MT4 accounts (terminals) that belong to your API key.
   * This is the call to make when you need the terminal ids of accounts that already exist - started
   * from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of
   * every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the
   * platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills
   * a terminal.
   * Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only
   * accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists
   * MT4 accounts).
   * ============================================================================
   * </pre>
   */
  public static final class AccountsStub extends io.grpc.stub.AbstractAsyncStub<AccountsStub> {
    private AccountsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists the accounts of the API key sent in the APIKey header, one page per call. Pass the
     * next_page_token of a page back as page_token to continue; an empty next_page_token means that was
     * the last page.
     * [DefaultValues]
     * {
     *   "includeStopped": "false",
     *   "pageSize": "200"
     * }
     * </pre>
     */
    public void list(mt4_term_api.Mt4TermApiAccounts.AccountsListRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccounts.AccountsListReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Accounts: the MT4 accounts (terminals) that belong to your API key.
   * This is the call to make when you need the terminal ids of accounts that already exist - started
   * from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of
   * every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the
   * platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills
   * a terminal.
   * Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only
   * accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists
   * MT4 accounts).
   * ============================================================================
   * </pre>
   */
  public static final class AccountsBlockingStub extends io.grpc.stub.AbstractBlockingStub<AccountsBlockingStub> {
    private AccountsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists the accounts of the API key sent in the APIKey header, one page per call. Pass the
     * next_page_token of a page back as page_token to continue; an empty next_page_token means that was
     * the last page.
     * [DefaultValues]
     * {
     *   "includeStopped": "false",
     *   "pageSize": "200"
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiAccounts.AccountsListReply list(mt4_term_api.Mt4TermApiAccounts.AccountsListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Accounts: the MT4 accounts (terminals) that belong to your API key.
   * This is the call to make when you need the terminal ids of accounts that already exist - started
   * from the dashboard or by an earlier ConnectEx - so that you can send them as the "id" header of
   * every other call. Unlike the terminal calls it needs NO "id" header: it is answered from the
   * platform's records of the API key sent in the "APIKey" header, and it never starts, wakes or bills
   * a terminal.
   * Accounts are listed newest first (by started_utc, then terminal_id) and returned by page. Only
   * accounts of THIS platform's endpoint are listed (mt5.mrpc.pro lists MT5 accounts, mt4.mrpc.pro lists
   * MT4 accounts).
   * ============================================================================
   * </pre>
   */
  public static final class AccountsFutureStub extends io.grpc.stub.AbstractFutureStub<AccountsFutureStub> {
    private AccountsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists the accounts of the API key sent in the APIKey header, one page per call. Pass the
     * next_page_token of a page back as page_token to continue; an empty next_page_token means that was
     * the last page.
     * [DefaultValues]
     * {
     *   "includeStopped": "false",
     *   "pageSize": "200"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiAccounts.AccountsListReply> list(
        mt4_term_api.Mt4TermApiAccounts.AccountsListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AccountsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AccountsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((mt4_term_api.Mt4TermApiAccounts.AccountsListRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiAccounts.AccountsListReply>) responseObserver);
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

  private static abstract class AccountsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiAccounts.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Accounts");
    }
  }

  private static final class AccountsFileDescriptorSupplier
      extends AccountsBaseDescriptorSupplier {
    AccountsFileDescriptorSupplier() {}
  }

  private static final class AccountsMethodDescriptorSupplier
      extends AccountsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AccountsMethodDescriptorSupplier(String methodName) {
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
      synchronized (AccountsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountsFileDescriptorSupplier())
              .addMethod(getListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
