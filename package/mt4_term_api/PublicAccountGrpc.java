package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================================
 * PublicAccount: publish the MT4 account in the "id" header as a public master account for copy trading on mrpc.pro,
 * change its public name and profit share, unpublish it, and read its public status.
 * The call needs only the "APIKey" header (your mrpc_live_ key) and the "id" header (the terminal), like every other
 * terminal call: no user key, no session, no admin key. The account must belong to the API key's user and be an MT4
 * account (mt4.mrpc.pro); any other id - another user's account, a deleted one, an MT5 one, an unknown one - is answered
 * PUBLIC_ACCOUNT_TERMINAL_NOT_FOUND. The rules are the control panel's (mrpc.pro/my, "Public profile"): a public name of
 * 2-32 letters, digits, spaces, "." "_" "-", and a profit share of 0-50 % (one decimal) charged to followers on winning
 * copied trades. The terminal does not have to be running; the leaderboard lists the account once its first figures have
 * been read from it (listed), which needs it connected.
 * ============================================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-public-account.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PublicAccountGrpc {

  private PublicAccountGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.PublicAccount";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest,
      mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getPublishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Publish",
      requestType = mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest.class,
      responseType = mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest,
      mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getPublishMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest, mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getPublishMethod;
    if ((getPublishMethod = PublicAccountGrpc.getPublishMethod) == null) {
      synchronized (PublicAccountGrpc.class) {
        if ((getPublishMethod = PublicAccountGrpc.getPublishMethod) == null) {
          PublicAccountGrpc.getPublishMethod = getPublishMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest, mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Publish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new PublicAccountMethodDescriptorSupplier("Publish"))
              .build();
        }
      }
    }
    return getPublishMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest,
      mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getUnpublishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Unpublish",
      requestType = mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest.class,
      responseType = mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest,
      mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getUnpublishMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest, mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getUnpublishMethod;
    if ((getUnpublishMethod = PublicAccountGrpc.getUnpublishMethod) == null) {
      synchronized (PublicAccountGrpc.class) {
        if ((getUnpublishMethod = PublicAccountGrpc.getUnpublishMethod) == null) {
          PublicAccountGrpc.getUnpublishMethod = getUnpublishMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest, mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Unpublish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new PublicAccountMethodDescriptorSupplier("Unpublish"))
              .build();
        }
      }
    }
    return getUnpublishMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest,
      mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Status",
      requestType = mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest.class,
      responseType = mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest,
      mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getStatusMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest, mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> getStatusMethod;
    if ((getStatusMethod = PublicAccountGrpc.getStatusMethod) == null) {
      synchronized (PublicAccountGrpc.class) {
        if ((getStatusMethod = PublicAccountGrpc.getStatusMethod) == null) {
          PublicAccountGrpc.getStatusMethod = getStatusMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest, mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Status"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new PublicAccountMethodDescriptorSupplier("Status"))
              .build();
        }
      }
    }
    return getStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PublicAccountStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PublicAccountStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PublicAccountStub>() {
        @java.lang.Override
        public PublicAccountStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PublicAccountStub(channel, callOptions);
        }
      };
    return PublicAccountStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PublicAccountBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PublicAccountBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PublicAccountBlockingStub>() {
        @java.lang.Override
        public PublicAccountBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PublicAccountBlockingStub(channel, callOptions);
        }
      };
    return PublicAccountBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PublicAccountFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PublicAccountFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PublicAccountFutureStub>() {
        @java.lang.Override
        public PublicAccountFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PublicAccountFutureStub(channel, callOptions);
        }
      };
    return PublicAccountFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================================
   * PublicAccount: publish the MT4 account in the "id" header as a public master account for copy trading on mrpc.pro,
   * change its public name and profit share, unpublish it, and read its public status.
   * The call needs only the "APIKey" header (your mrpc_live_ key) and the "id" header (the terminal), like every other
   * terminal call: no user key, no session, no admin key. The account must belong to the API key's user and be an MT4
   * account (mt4.mrpc.pro); any other id - another user's account, a deleted one, an MT5 one, an unknown one - is answered
   * PUBLIC_ACCOUNT_TERMINAL_NOT_FOUND. The rules are the control panel's (mrpc.pro/my, "Public profile"): a public name of
   * 2-32 letters, digits, spaces, "." "_" "-", and a profit share of 0-50 % (one decimal) charged to followers on winning
   * copied trades. The terminal does not have to be running; the leaderboard lists the account once its first figures have
   * been read from it (listed), which needs it connected.
   * ============================================================================
   * </pre>
   */
  public static abstract class PublicAccountImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Publishes the account, or changes the name or share of an account already public. A field left out keeps its current
     * value; on a first publish the name defaults to the terminal's name (else "MT4 " and the masked login) and the share to 0.
     * </pre>
     */
    public void publish(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishMethod(), responseObserver);
    }

    /**
     * <pre>
     * Unpublishes the account: it leaves the leaderboard and its page at once, and nobody can start following it. Copiers
     * already following it are not stopped by this call. The public id (slug), name and share are kept for a later Publish.
     * Unpublishing an account that is not public changes nothing.
     * </pre>
     */
    public void unpublish(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnpublishMethod(), responseObserver);
    }

    /**
     * <pre>
     * The account's public status. Changes nothing.
     * </pre>
     */
    public void status(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPublishMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest,
                mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>(
                  this, METHODID_PUBLISH)))
          .addMethod(
            getUnpublishMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest,
                mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>(
                  this, METHODID_UNPUBLISH)))
          .addMethod(
            getStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest,
                mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>(
                  this, METHODID_STATUS)))
          .build();
    }
  }

  /**
   * <pre>
   * ============================================================================
   * PublicAccount: publish the MT4 account in the "id" header as a public master account for copy trading on mrpc.pro,
   * change its public name and profit share, unpublish it, and read its public status.
   * The call needs only the "APIKey" header (your mrpc_live_ key) and the "id" header (the terminal), like every other
   * terminal call: no user key, no session, no admin key. The account must belong to the API key's user and be an MT4
   * account (mt4.mrpc.pro); any other id - another user's account, a deleted one, an MT5 one, an unknown one - is answered
   * PUBLIC_ACCOUNT_TERMINAL_NOT_FOUND. The rules are the control panel's (mrpc.pro/my, "Public profile"): a public name of
   * 2-32 letters, digits, spaces, "." "_" "-", and a profit share of 0-50 % (one decimal) charged to followers on winning
   * copied trades. The terminal does not have to be running; the leaderboard lists the account once its first figures have
   * been read from it (listed), which needs it connected.
   * ============================================================================
   * </pre>
   */
  public static final class PublicAccountStub extends io.grpc.stub.AbstractAsyncStub<PublicAccountStub> {
    private PublicAccountStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PublicAccountStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PublicAccountStub(channel, callOptions);
    }

    /**
     * <pre>
     * Publishes the account, or changes the name or share of an account already public. A field left out keeps its current
     * value; on a first publish the name defaults to the terminal's name (else "MT4 " and the masked login) and the share to 0.
     * </pre>
     */
    public void publish(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Unpublishes the account: it leaves the leaderboard and its page at once, and nobody can start following it. Copiers
     * already following it are not stopped by this call. The public id (slug), name and share are kept for a later Publish.
     * Unpublishing an account that is not public changes nothing.
     * </pre>
     */
    public void unpublish(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnpublishMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * The account's public status. Changes nothing.
     * </pre>
     */
    public void status(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * PublicAccount: publish the MT4 account in the "id" header as a public master account for copy trading on mrpc.pro,
   * change its public name and profit share, unpublish it, and read its public status.
   * The call needs only the "APIKey" header (your mrpc_live_ key) and the "id" header (the terminal), like every other
   * terminal call: no user key, no session, no admin key. The account must belong to the API key's user and be an MT4
   * account (mt4.mrpc.pro); any other id - another user's account, a deleted one, an MT5 one, an unknown one - is answered
   * PUBLIC_ACCOUNT_TERMINAL_NOT_FOUND. The rules are the control panel's (mrpc.pro/my, "Public profile"): a public name of
   * 2-32 letters, digits, spaces, "." "_" "-", and a profit share of 0-50 % (one decimal) charged to followers on winning
   * copied trades. The terminal does not have to be running; the leaderboard lists the account once its first figures have
   * been read from it (listed), which needs it connected.
   * ============================================================================
   * </pre>
   */
  public static final class PublicAccountBlockingStub extends io.grpc.stub.AbstractBlockingStub<PublicAccountBlockingStub> {
    private PublicAccountBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PublicAccountBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PublicAccountBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Publishes the account, or changes the name or share of an account already public. A field left out keeps its current
     * value; on a first publish the name defaults to the terminal's name (else "MT4 " and the masked login) and the share to 0.
     * </pre>
     */
    public mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply publish(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Unpublishes the account: it leaves the leaderboard and its page at once, and nobody can start following it. Copiers
     * already following it are not stopped by this call. The public id (slug), name and share are kept for a later Publish.
     * Unpublishing an account that is not public changes nothing.
     * </pre>
     */
    public mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply unpublish(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnpublishMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * The account's public status. Changes nothing.
     * </pre>
     */
    public mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply status(mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * PublicAccount: publish the MT4 account in the "id" header as a public master account for copy trading on mrpc.pro,
   * change its public name and profit share, unpublish it, and read its public status.
   * The call needs only the "APIKey" header (your mrpc_live_ key) and the "id" header (the terminal), like every other
   * terminal call: no user key, no session, no admin key. The account must belong to the API key's user and be an MT4
   * account (mt4.mrpc.pro); any other id - another user's account, a deleted one, an MT5 one, an unknown one - is answered
   * PUBLIC_ACCOUNT_TERMINAL_NOT_FOUND. The rules are the control panel's (mrpc.pro/my, "Public profile"): a public name of
   * 2-32 letters, digits, spaces, "." "_" "-", and a profit share of 0-50 % (one decimal) charged to followers on winning
   * copied trades. The terminal does not have to be running; the leaderboard lists the account once its first figures have
   * been read from it (listed), which needs it connected.
   * ============================================================================
   * </pre>
   */
  public static final class PublicAccountFutureStub extends io.grpc.stub.AbstractFutureStub<PublicAccountFutureStub> {
    private PublicAccountFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PublicAccountFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PublicAccountFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Publishes the account, or changes the name or share of an account already public. A field left out keeps its current
     * value; on a first publish the name defaults to the terminal's name (else "MT4 " and the masked login) and the share to 0.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> publish(
        mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Unpublishes the account: it leaves the leaderboard and its page at once, and nobody can start following it. Copiers
     * already following it are not stopped by this call. The public id (slug), name and share are kept for a later Publish.
     * Unpublishing an account that is not public changes nothing.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> unpublish(
        mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnpublishMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * The account's public status. Changes nothing.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply> status(
        mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUBLISH = 0;
  private static final int METHODID_UNPUBLISH = 1;
  private static final int METHODID_STATUS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PublicAccountImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PublicAccountImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUBLISH:
          serviceImpl.publish((mt4_term_api.Mt4TermApiPublicAccount.PublicAccountPublishRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>) responseObserver);
          break;
        case METHODID_UNPUBLISH:
          serviceImpl.unpublish((mt4_term_api.Mt4TermApiPublicAccount.PublicAccountUnpublishRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>) responseObserver);
          break;
        case METHODID_STATUS:
          serviceImpl.status((mt4_term_api.Mt4TermApiPublicAccount.PublicAccountStatusRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiPublicAccount.PublicAccountReply>) responseObserver);
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

  private static abstract class PublicAccountBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PublicAccountBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiPublicAccount.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PublicAccount");
    }
  }

  private static final class PublicAccountFileDescriptorSupplier
      extends PublicAccountBaseDescriptorSupplier {
    PublicAccountFileDescriptorSupplier() {}
  }

  private static final class PublicAccountMethodDescriptorSupplier
      extends PublicAccountBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PublicAccountMethodDescriptorSupplier(String methodName) {
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
      synchronized (PublicAccountGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PublicAccountFileDescriptorSupplier())
              .addMethod(getPublishMethod())
              .addMethod(getUnpublishMethod())
              .addMethod(getStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
