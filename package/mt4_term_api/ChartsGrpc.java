package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-charts.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChartsGrpc {

  private ChartsGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.Charts";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest,
      mt4_term_api.Mt4TermApiCharts.GetEaParamsReply> getGetEaParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEaParams",
      requestType = mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest.class,
      responseType = mt4_term_api.Mt4TermApiCharts.GetEaParamsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest,
      mt4_term_api.Mt4TermApiCharts.GetEaParamsReply> getGetEaParamsMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest, mt4_term_api.Mt4TermApiCharts.GetEaParamsReply> getGetEaParamsMethod;
    if ((getGetEaParamsMethod = ChartsGrpc.getGetEaParamsMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getGetEaParamsMethod = ChartsGrpc.getGetEaParamsMethod) == null) {
          ChartsGrpc.getGetEaParamsMethod = getGetEaParamsMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest, mt4_term_api.Mt4TermApiCharts.GetEaParamsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEaParams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiCharts.GetEaParamsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("GetEaParams"))
              .build();
        }
      }
    }
    return getGetEaParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest,
      mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply> getOpenTerminalChartWithEaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenTerminalChartWithEa",
      requestType = mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest.class,
      responseType = mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest,
      mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply> getOpenTerminalChartWithEaMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest, mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply> getOpenTerminalChartWithEaMethod;
    if ((getOpenTerminalChartWithEaMethod = ChartsGrpc.getOpenTerminalChartWithEaMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getOpenTerminalChartWithEaMethod = ChartsGrpc.getOpenTerminalChartWithEaMethod) == null) {
          ChartsGrpc.getOpenTerminalChartWithEaMethod = getOpenTerminalChartWithEaMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest, mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenTerminalChartWithEa"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("OpenTerminalChartWithEa"))
              .build();
        }
      }
    }
    return getOpenTerminalChartWithEaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChartsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChartsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChartsStub>() {
        @java.lang.Override
        public ChartsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChartsStub(channel, callOptions);
        }
      };
    return ChartsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChartsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChartsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChartsBlockingStub>() {
        @java.lang.Override
        public ChartsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChartsBlockingStub(channel, callOptions);
        }
      };
    return ChartsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChartsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChartsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChartsFutureStub>() {
        @java.lang.Override
        public ChartsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChartsFutureStub(channel, callOptions);
        }
      };
    return ChartsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ChartsImplBase implements io.grpc.BindableService {

    /**
     */
    public void getEaParams(mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiCharts.GetEaParamsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEaParamsMethod(), responseObserver);
    }

    /**
     */
    public void openTerminalChartWithEa(mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenTerminalChartWithEaMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetEaParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest,
                mt4_term_api.Mt4TermApiCharts.GetEaParamsReply>(
                  this, METHODID_GET_EA_PARAMS)))
          .addMethod(
            getOpenTerminalChartWithEaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest,
                mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply>(
                  this, METHODID_OPEN_TERMINAL_CHART_WITH_EA)))
          .build();
    }
  }

  /**
   */
  public static final class ChartsStub extends io.grpc.stub.AbstractAsyncStub<ChartsStub> {
    private ChartsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChartsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChartsStub(channel, callOptions);
    }

    /**
     */
    public void getEaParams(mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiCharts.GetEaParamsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEaParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void openTerminalChartWithEa(mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenTerminalChartWithEaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChartsBlockingStub extends io.grpc.stub.AbstractBlockingStub<ChartsBlockingStub> {
    private ChartsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChartsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChartsBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiCharts.GetEaParamsReply getEaParams(mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEaParamsMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply openTerminalChartWithEa(mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenTerminalChartWithEaMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChartsFutureStub extends io.grpc.stub.AbstractFutureStub<ChartsFutureStub> {
    private ChartsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChartsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChartsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiCharts.GetEaParamsReply> getEaParams(
        mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEaParamsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply> openTerminalChartWithEa(
        mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenTerminalChartWithEaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_EA_PARAMS = 0;
  private static final int METHODID_OPEN_TERMINAL_CHART_WITH_EA = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChartsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChartsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_EA_PARAMS:
          serviceImpl.getEaParams((mt4_term_api.Mt4TermApiCharts.GetEaParamsRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiCharts.GetEaParamsReply>) responseObserver);
          break;
        case METHODID_OPEN_TERMINAL_CHART_WITH_EA:
          serviceImpl.openTerminalChartWithEa((mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiCharts.OpenTerminalChartWithEaReply>) responseObserver);
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

  private static abstract class ChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChartsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiCharts.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Charts");
    }
  }

  private static final class ChartsFileDescriptorSupplier
      extends ChartsBaseDescriptorSupplier {
    ChartsFileDescriptorSupplier() {}
  }

  private static final class ChartsMethodDescriptorSupplier
      extends ChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChartsMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChartsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChartsFileDescriptorSupplier())
              .addMethod(getGetEaParamsMethod())
              .addMethod(getOpenTerminalChartWithEaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
