package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Demo account creation service. Automates the MT4 "Open an Account" wizard via
 * Win32 GUI automation (proxied to the terminal's Gui service).
 * Does NOT require 'id' header — auto-picks any available terminal.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-demo-account.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DemoAccountGrpc {

  private DemoAccountGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.DemoAccount";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest,
      mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> getOpenDemoAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenDemoAccount",
      requestType = mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest.class,
      responseType = mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest,
      mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> getOpenDemoAccountMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest, mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> getOpenDemoAccountMethod;
    if ((getOpenDemoAccountMethod = DemoAccountGrpc.getOpenDemoAccountMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getOpenDemoAccountMethod = DemoAccountGrpc.getOpenDemoAccountMethod) == null) {
          DemoAccountGrpc.getOpenDemoAccountMethod = getOpenDemoAccountMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest, mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenDemoAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("OpenDemoAccount"))
              .build();
        }
      }
    }
    return getOpenDemoAccountMethod;
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
    if ((getDemoOpenAccountInteractiveMethod = DemoAccountGrpc.getDemoOpenAccountInteractiveMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getDemoOpenAccountInteractiveMethod = DemoAccountGrpc.getDemoOpenAccountInteractiveMethod) == null) {
          DemoAccountGrpc.getDemoOpenAccountInteractiveMethod = getDemoOpenAccountInteractiveMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage, mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccountInteractive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("DemoOpenAccountInteractive"))
              .build();
        }
      }
    }
    return getDemoOpenAccountInteractiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DemoAccountStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DemoAccountStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DemoAccountStub>() {
        @java.lang.Override
        public DemoAccountStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DemoAccountStub(channel, callOptions);
        }
      };
    return DemoAccountStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DemoAccountBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DemoAccountBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DemoAccountBlockingStub>() {
        @java.lang.Override
        public DemoAccountBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DemoAccountBlockingStub(channel, callOptions);
        }
      };
    return DemoAccountBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DemoAccountFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DemoAccountFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DemoAccountFutureStub>() {
        @java.lang.Override
        public DemoAccountFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DemoAccountFutureStub(channel, callOptions);
        }
      };
    return DemoAccountFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Demo account creation service. Automates the MT4 "Open an Account" wizard via
   * Win32 GUI automation (proxied to the terminal's Gui service).
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static abstract class DemoAccountImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Open a demo account. Full wizard flow: search -&gt; select -&gt; fill form -&gt; register.
     * [DefaultValues]
     * { "company": "MetaQuotes Ltd.", "firstName": "Test", "lastName": "User",
     *   "email": "test&#64;test.com", "phone": "+1234567890", "timeoutSeconds": "60" }
     * </pre>
     */
    public void openDemoAccount(mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenDemoAccountMethod(), responseObserver);
    }

    /**
     * <pre>
     * Interactive step-by-step demo account opening wizard.
     * Bidirectional streaming session: search company -&gt; select -&gt; form schema -&gt; submit -&gt; 2FA (if any) -&gt; completed.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDemoOpenAccountInteractiveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOpenDemoAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest,
                mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply>(
                  this, METHODID_OPEN_DEMO_ACCOUNT)))
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
   * Demo account creation service. Automates the MT4 "Open an Account" wizard via
   * Win32 GUI automation (proxied to the terminal's Gui service).
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static final class DemoAccountStub extends io.grpc.stub.AbstractAsyncStub<DemoAccountStub> {
    private DemoAccountStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoAccountStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DemoAccountStub(channel, callOptions);
    }

    /**
     * <pre>
     * Open a demo account. Full wizard flow: search -&gt; select -&gt; fill form -&gt; register.
     * [DefaultValues]
     * { "company": "MetaQuotes Ltd.", "firstName": "Test", "lastName": "User",
     *   "email": "test&#64;test.com", "phone": "+1234567890", "timeoutSeconds": "60" }
     * </pre>
     */
    public void openDemoAccount(mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenDemoAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Interactive step-by-step demo account opening wizard.
     * Bidirectional streaming session: search company -&gt; select -&gt; form schema -&gt; submit -&gt; 2FA (if any) -&gt; completed.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDemoOpenAccountInteractiveMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Demo account creation service. Automates the MT4 "Open an Account" wizard via
   * Win32 GUI automation (proxied to the terminal's Gui service).
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static final class DemoAccountBlockingStub extends io.grpc.stub.AbstractBlockingStub<DemoAccountBlockingStub> {
    private DemoAccountBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoAccountBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DemoAccountBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Open a demo account. Full wizard flow: search -&gt; select -&gt; fill form -&gt; register.
     * [DefaultValues]
     * { "company": "MetaQuotes Ltd.", "firstName": "Test", "lastName": "User",
     *   "email": "test&#64;test.com", "phone": "+1234567890", "timeoutSeconds": "60" }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply openDemoAccount(mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenDemoAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Demo account creation service. Automates the MT4 "Open an Account" wizard via
   * Win32 GUI automation (proxied to the terminal's Gui service).
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static final class DemoAccountFutureStub extends io.grpc.stub.AbstractFutureStub<DemoAccountFutureStub> {
    private DemoAccountFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoAccountFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DemoAccountFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Open a demo account. Full wizard flow: search -&gt; select -&gt; fill form -&gt; register.
     * [DefaultValues]
     * { "company": "MetaQuotes Ltd.", "firstName": "Test", "lastName": "User",
     *   "email": "test&#64;test.com", "phone": "+1234567890", "timeoutSeconds": "60" }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountReply> openDemoAccount(
        mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenDemoAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OPEN_DEMO_ACCOUNT = 0;
  private static final int METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DemoAccountImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DemoAccountImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OPEN_DEMO_ACCOUNT:
          serviceImpl.openDemoAccount((mt4_term_api.Mt4TermApiGui.GuiDemoOpenAccountRequest) request,
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

  private static abstract class DemoAccountBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DemoAccountBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiDemoAccount.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DemoAccount");
    }
  }

  private static final class DemoAccountFileDescriptorSupplier
      extends DemoAccountBaseDescriptorSupplier {
    DemoAccountFileDescriptorSupplier() {}
  }

  private static final class DemoAccountMethodDescriptorSupplier
      extends DemoAccountBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DemoAccountMethodDescriptorSupplier(String methodName) {
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
      synchronized (DemoAccountGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DemoAccountFileDescriptorSupplier())
              .addMethod(getOpenDemoAccountMethod())
              .addMethod(getDemoOpenAccountInteractiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
