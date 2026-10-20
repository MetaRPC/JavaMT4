package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Terminal-side GUI-automation service (implemented by mt4-framework-grpc, which
 * P/Invokes mt4-term-gui.dll). The manager's DemoAccount service proxies to these.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-gui.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GuiGrpc {

  private GuiGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.Gui";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest,
      mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> getDemoOpenAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoOpenAccount",
      requestType = mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest.class,
      responseType = mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest,
      mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> getDemoOpenAccountMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest, mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> getDemoOpenAccountMethod;
    if ((getDemoOpenAccountMethod = GuiGrpc.getDemoOpenAccountMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoOpenAccountMethod = GuiGrpc.getDemoOpenAccountMethod) == null) {
          GuiGrpc.getDemoOpenAccountMethod = getDemoOpenAccountMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest, mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoOpenAccount"))
              .build();
        }
      }
    }
    return getDemoOpenAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage,
      mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> getDemoOpenAccountInteractiveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoOpenAccountInteractive",
      requestType = mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage.class,
      responseType = mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage,
      mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> getDemoOpenAccountInteractiveMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage, mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> getDemoOpenAccountInteractiveMethod;
    if ((getDemoOpenAccountInteractiveMethod = GuiGrpc.getDemoOpenAccountInteractiveMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoOpenAccountInteractiveMethod = GuiGrpc.getDemoOpenAccountInteractiveMethod) == null) {
          GuiGrpc.getDemoOpenAccountInteractiveMethod = getDemoOpenAccountInteractiveMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage, mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccountInteractive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoOpenAccountInteractive"))
              .build();
        }
      }
    }
    return getDemoOpenAccountInteractiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GuiStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GuiStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GuiStub>() {
        @java.lang.Override
        public GuiStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GuiStub(channel, callOptions);
        }
      };
    return GuiStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GuiBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GuiBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GuiBlockingStub>() {
        @java.lang.Override
        public GuiBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GuiBlockingStub(channel, callOptions);
        }
      };
    return GuiBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GuiFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GuiFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GuiFutureStub>() {
        @java.lang.Override
        public GuiFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GuiFutureStub(channel, callOptions);
        }
      };
    return GuiFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Terminal-side GUI-automation service (implemented by mt4-framework-grpc, which
   * P/Invokes mt4-term-gui.dll). The manager's DemoAccount service proxies to these.
   * </pre>
   */
  public static abstract class GuiImplBase implements io.grpc.BindableService {

    /**
     */
    public void demoOpenAccount(mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoOpenAccountMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDemoOpenAccountInteractiveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDemoOpenAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest,
                mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply>(
                  this, METHODID_DEMO_OPEN_ACCOUNT)))
          .addMethod(
            getDemoOpenAccountInteractiveMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage,
                mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage>(
                  this, METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE)))
          .build();
    }
  }

  /**
   * <pre>
   * Terminal-side GUI-automation service (implemented by mt4-framework-grpc, which
   * P/Invokes mt4-term-gui.dll). The manager's DemoAccount service proxies to these.
   * </pre>
   */
  public static final class GuiStub extends io.grpc.stub.AbstractAsyncStub<GuiStub> {
    private GuiStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GuiStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GuiStub(channel, callOptions);
    }

    /**
     */
    public void demoOpenAccount(mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDemoOpenAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDemoOpenAccountInteractiveMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Terminal-side GUI-automation service (implemented by mt4-framework-grpc, which
   * P/Invokes mt4-term-gui.dll). The manager's DemoAccount service proxies to these.
   * </pre>
   */
  public static final class GuiBlockingStub extends io.grpc.stub.AbstractBlockingStub<GuiBlockingStub> {
    private GuiBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GuiBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GuiBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply demoOpenAccount(mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDemoOpenAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Terminal-side GUI-automation service (implemented by mt4-framework-grpc, which
   * P/Invokes mt4-term-gui.dll). The manager's DemoAccount service proxies to these.
   * </pre>
   */
  public static final class GuiFutureStub extends io.grpc.stub.AbstractFutureStub<GuiFutureStub> {
    private GuiFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GuiFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GuiFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> demoOpenAccount(
        mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDemoOpenAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DEMO_OPEN_ACCOUNT = 0;
  private static final int METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GuiImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GuiImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DEMO_OPEN_ACCOUNT:
          serviceImpl.demoOpenAccount((mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply>) responseObserver);
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
        case METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.demoOpenAccountInteractive(
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GuiBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GuiBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiGui.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Gui");
    }
  }

  private static final class GuiFileDescriptorSupplier
      extends GuiBaseDescriptorSupplier {
    GuiFileDescriptorSupplier() {}
  }

  private static final class GuiMethodDescriptorSupplier
      extends GuiBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GuiMethodDescriptorSupplier(String methodName) {
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
      synchronized (GuiGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GuiFileDescriptorSupplier())
              .addMethod(getDemoOpenAccountMethod())
              .addMethod(getDemoOpenAccountInteractiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
