package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-health-check.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HealthServiceGrpc {

  private HealthServiceGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.HealthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest,
      mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply> getHealthCheckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HealthCheck",
      requestType = mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest.class,
      responseType = mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest,
      mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply> getHealthCheckMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest, mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply> getHealthCheckMethod;
    if ((getHealthCheckMethod = HealthServiceGrpc.getHealthCheckMethod) == null) {
      synchronized (HealthServiceGrpc.class) {
        if ((getHealthCheckMethod = HealthServiceGrpc.getHealthCheckMethod) == null) {
          HealthServiceGrpc.getHealthCheckMethod = getHealthCheckMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest, mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HealthCheck"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply.getDefaultInstance()))
              .setSchemaDescriptor(new HealthServiceMethodDescriptorSupplier("HealthCheck"))
              .build();
        }
      }
    }
    return getHealthCheckMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest,
      mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply> getCloseTerminalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CloseTerminal",
      requestType = mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest.class,
      responseType = mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest,
      mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply> getCloseTerminalMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest, mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply> getCloseTerminalMethod;
    if ((getCloseTerminalMethod = HealthServiceGrpc.getCloseTerminalMethod) == null) {
      synchronized (HealthServiceGrpc.class) {
        if ((getCloseTerminalMethod = HealthServiceGrpc.getCloseTerminalMethod) == null) {
          HealthServiceGrpc.getCloseTerminalMethod = getCloseTerminalMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest, mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CloseTerminal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply.getDefaultInstance()))
              .setSchemaDescriptor(new HealthServiceMethodDescriptorSupplier("CloseTerminal"))
              .build();
        }
      }
    }
    return getCloseTerminalMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HealthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthServiceStub>() {
        @java.lang.Override
        public HealthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthServiceStub(channel, callOptions);
        }
      };
    return HealthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HealthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthServiceBlockingStub>() {
        @java.lang.Override
        public HealthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthServiceBlockingStub(channel, callOptions);
        }
      };
    return HealthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HealthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthServiceFutureStub>() {
        @java.lang.Override
        public HealthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthServiceFutureStub(channel, callOptions);
        }
      };
    return HealthServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class HealthServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void healthCheck(mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHealthCheckMethod(), responseObserver);
    }

    /**
     */
    public void closeTerminal(mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCloseTerminalMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHealthCheckMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest,
                mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply>(
                  this, METHODID_HEALTH_CHECK)))
          .addMethod(
            getCloseTerminalMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest,
                mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply>(
                  this, METHODID_CLOSE_TERMINAL)))
          .build();
    }
  }

  /**
   */
  public static final class HealthServiceStub extends io.grpc.stub.AbstractAsyncStub<HealthServiceStub> {
    private HealthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthServiceStub(channel, callOptions);
    }

    /**
     */
    public void healthCheck(mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHealthCheckMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void closeTerminal(mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCloseTerminalMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HealthServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<HealthServiceBlockingStub> {
    private HealthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply healthCheck(mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHealthCheckMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply closeTerminal(mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCloseTerminalMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HealthServiceFutureStub extends io.grpc.stub.AbstractFutureStub<HealthServiceFutureStub> {
    private HealthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply> healthCheck(
        mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHealthCheckMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply> closeTerminal(
        mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCloseTerminalMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HEALTH_CHECK = 0;
  private static final int METHODID_CLOSE_TERMINAL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HealthServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HealthServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HEALTH_CHECK:
          serviceImpl.healthCheck((mt4_term_api.Mt4TermApiHealthCheck.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiHealthCheck.HealthCheckReply>) responseObserver);
          break;
        case METHODID_CLOSE_TERMINAL:
          serviceImpl.closeTerminal((mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiHealthCheck.CloseTerminalReply>) responseObserver);
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

  private static abstract class HealthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HealthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiHealthCheck.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HealthService");
    }
  }

  private static final class HealthServiceFileDescriptorSupplier
      extends HealthServiceBaseDescriptorSupplier {
    HealthServiceFileDescriptorSupplier() {}
  }

  private static final class HealthServiceMethodDescriptorSupplier
      extends HealthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HealthServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (HealthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HealthServiceFileDescriptorSupplier())
              .addMethod(getHealthCheckMethod())
              .addMethod(getCloseTerminalMethod())
              .build();
        }
      }
    }
    return result;
  }
}
