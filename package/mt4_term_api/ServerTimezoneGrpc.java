package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================================
 * ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from.
 * Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it
 * knows (the terminal is not asked unless nothing is known yet), so it is cheap to call.
 * ============================================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-server-timezone.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServerTimezoneGrpc {

  private ServerTimezoneGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.ServerTimezone";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest,
      mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply> getServerTimezoneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ServerTimezone",
      requestType = mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest.class,
      responseType = mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest,
      mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply> getServerTimezoneMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest, mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply> getServerTimezoneMethod;
    if ((getServerTimezoneMethod = ServerTimezoneGrpc.getServerTimezoneMethod) == null) {
      synchronized (ServerTimezoneGrpc.class) {
        if ((getServerTimezoneMethod = ServerTimezoneGrpc.getServerTimezoneMethod) == null) {
          ServerTimezoneGrpc.getServerTimezoneMethod = getServerTimezoneMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest, mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServerTimezone"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerTimezoneMethodDescriptorSupplier("ServerTimezone"))
              .build();
        }
      }
    }
    return getServerTimezoneMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerTimezoneStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerTimezoneStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerTimezoneStub>() {
        @java.lang.Override
        public ServerTimezoneStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerTimezoneStub(channel, callOptions);
        }
      };
    return ServerTimezoneStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerTimezoneBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerTimezoneBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerTimezoneBlockingStub>() {
        @java.lang.Override
        public ServerTimezoneBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerTimezoneBlockingStub(channel, callOptions);
        }
      };
    return ServerTimezoneBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerTimezoneFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerTimezoneFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerTimezoneFutureStub>() {
        @java.lang.Override
        public ServerTimezoneFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerTimezoneFutureStub(channel, callOptions);
        }
      };
    return ServerTimezoneFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================================
   * ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from.
   * Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it
   * knows (the terminal is not asked unless nothing is known yet), so it is cheap to call.
   * ============================================================================
   * </pre>
   */
  public static abstract class ServerTimezoneImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * The broker server's UTC offset of the terminal in the "id" header: live (read by this terminal from a fresh quote) or the
     * last one validated for the same broker server (an account whose market is closed has no quote to read it from - MT4 only;
     * MT5 reads its server clock directly and is always live).
     * </pre>
     */
    public void serverTimezone(mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServerTimezoneMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getServerTimezoneMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest,
                mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply>(
                  this, METHODID_SERVER_TIMEZONE)))
          .build();
    }
  }

  /**
   * <pre>
   * ============================================================================
   * ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from.
   * Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it
   * knows (the terminal is not asked unless nothing is known yet), so it is cheap to call.
   * ============================================================================
   * </pre>
   */
  public static final class ServerTimezoneStub extends io.grpc.stub.AbstractAsyncStub<ServerTimezoneStub> {
    private ServerTimezoneStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerTimezoneStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerTimezoneStub(channel, callOptions);
    }

    /**
     * <pre>
     * The broker server's UTC offset of the terminal in the "id" header: live (read by this terminal from a fresh quote) or the
     * last one validated for the same broker server (an account whose market is closed has no quote to read it from - MT4 only;
     * MT5 reads its server clock directly and is always live).
     * </pre>
     */
    public void serverTimezone(mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getServerTimezoneMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from.
   * Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it
   * knows (the terminal is not asked unless nothing is known yet), so it is cheap to call.
   * ============================================================================
   * </pre>
   */
  public static final class ServerTimezoneBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServerTimezoneBlockingStub> {
    private ServerTimezoneBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerTimezoneBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerTimezoneBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * The broker server's UTC offset of the terminal in the "id" header: live (read by this terminal from a fresh quote) or the
     * last one validated for the same broker server (an account whose market is closed has no quote to read it from - MT4 only;
     * MT5 reads its server clock directly and is always live).
     * </pre>
     */
    public mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply serverTimezone(mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getServerTimezoneMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * ServerTimezone: the broker server's time zone the platform uses for this account, and where it came from.
   * Every *_utc field of the API is the broker server time minus this offset. Answered by the platform itself from what it
   * knows (the terminal is not asked unless nothing is known yet), so it is cheap to call.
   * ============================================================================
   * </pre>
   */
  public static final class ServerTimezoneFutureStub extends io.grpc.stub.AbstractFutureStub<ServerTimezoneFutureStub> {
    private ServerTimezoneFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerTimezoneFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerTimezoneFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * The broker server's UTC offset of the terminal in the "id" header: live (read by this terminal from a fresh quote) or the
     * last one validated for the same broker server (an account whose market is closed has no quote to read it from - MT4 only;
     * MT5 reads its server clock directly and is always live).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply> serverTimezone(
        mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getServerTimezoneMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SERVER_TIMEZONE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerTimezoneImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerTimezoneImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SERVER_TIMEZONE:
          serviceImpl.serverTimezone((mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiServerTimezone.ServerTimezoneReply>) responseObserver);
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

  private static abstract class ServerTimezoneBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerTimezoneBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiServerTimezone.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServerTimezone");
    }
  }

  private static final class ServerTimezoneFileDescriptorSupplier
      extends ServerTimezoneBaseDescriptorSupplier {
    ServerTimezoneFileDescriptorSupplier() {}
  }

  private static final class ServerTimezoneMethodDescriptorSupplier
      extends ServerTimezoneBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerTimezoneMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServerTimezoneGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerTimezoneFileDescriptorSupplier())
              .addMethod(getServerTimezoneMethod())
              .build();
        }
      }
    }
    return result;
  }
}
