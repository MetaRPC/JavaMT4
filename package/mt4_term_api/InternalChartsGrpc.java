package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-internal-charts.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class InternalChartsGrpc {

  private InternalChartsGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.InternalCharts";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest,
      mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply> getChartOpenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ChartOpen",
      requestType = mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest.class,
      responseType = mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest,
      mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply> getChartOpenMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest, mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply> getChartOpenMethod;
    if ((getChartOpenMethod = InternalChartsGrpc.getChartOpenMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getChartOpenMethod = InternalChartsGrpc.getChartOpenMethod) == null) {
          InternalChartsGrpc.getChartOpenMethod = getChartOpenMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest, mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ChartOpen"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("ChartOpen"))
              .build();
        }
      }
    }
    return getChartOpenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest,
      mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply> getChartCloseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ChartClose",
      requestType = mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest.class,
      responseType = mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest,
      mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply> getChartCloseMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest, mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply> getChartCloseMethod;
    if ((getChartCloseMethod = InternalChartsGrpc.getChartCloseMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getChartCloseMethod = InternalChartsGrpc.getChartCloseMethod) == null) {
          InternalChartsGrpc.getChartCloseMethod = getChartCloseMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest, mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ChartClose"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("ChartClose"))
              .build();
        }
      }
    }
    return getChartCloseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest,
      mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply> getCustomChartOpenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CustomChartOpen",
      requestType = mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest.class,
      responseType = mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest,
      mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply> getCustomChartOpenMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest, mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply> getCustomChartOpenMethod;
    if ((getCustomChartOpenMethod = InternalChartsGrpc.getCustomChartOpenMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getCustomChartOpenMethod = InternalChartsGrpc.getCustomChartOpenMethod) == null) {
          InternalChartsGrpc.getCustomChartOpenMethod = getCustomChartOpenMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest, mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CustomChartOpen"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("CustomChartOpen"))
              .build();
        }
      }
    }
    return getCustomChartOpenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest,
      mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply> getCustomChartSaveTemplateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CustomChartSaveTemplate",
      requestType = mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest.class,
      responseType = mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest,
      mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply> getCustomChartSaveTemplateMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest, mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply> getCustomChartSaveTemplateMethod;
    if ((getCustomChartSaveTemplateMethod = InternalChartsGrpc.getCustomChartSaveTemplateMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getCustomChartSaveTemplateMethod = InternalChartsGrpc.getCustomChartSaveTemplateMethod) == null) {
          InternalChartsGrpc.getCustomChartSaveTemplateMethod = getCustomChartSaveTemplateMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest, mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CustomChartSaveTemplate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("CustomChartSaveTemplate"))
              .build();
        }
      }
    }
    return getCustomChartSaveTemplateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest,
      mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply> getCustomChartApplyTemplateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CustomChartApplyTemplate",
      requestType = mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest.class,
      responseType = mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest,
      mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply> getCustomChartApplyTemplateMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest, mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply> getCustomChartApplyTemplateMethod;
    if ((getCustomChartApplyTemplateMethod = InternalChartsGrpc.getCustomChartApplyTemplateMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getCustomChartApplyTemplateMethod = InternalChartsGrpc.getCustomChartApplyTemplateMethod) == null) {
          InternalChartsGrpc.getCustomChartApplyTemplateMethod = getCustomChartApplyTemplateMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest, mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CustomChartApplyTemplate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("CustomChartApplyTemplate"))
              .build();
        }
      }
    }
    return getCustomChartApplyTemplateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InternalChartsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InternalChartsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InternalChartsStub>() {
        @java.lang.Override
        public InternalChartsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InternalChartsStub(channel, callOptions);
        }
      };
    return InternalChartsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InternalChartsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InternalChartsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InternalChartsBlockingStub>() {
        @java.lang.Override
        public InternalChartsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InternalChartsBlockingStub(channel, callOptions);
        }
      };
    return InternalChartsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InternalChartsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InternalChartsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InternalChartsFutureStub>() {
        @java.lang.Override
        public InternalChartsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InternalChartsFutureStub(channel, callOptions);
        }
      };
    return InternalChartsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class InternalChartsImplBase implements io.grpc.BindableService {

    /**
     */
    public void chartOpen(mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getChartOpenMethod(), responseObserver);
    }

    /**
     */
    public void chartClose(mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getChartCloseMethod(), responseObserver);
    }

    /**
     */
    public void customChartOpen(mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCustomChartOpenMethod(), responseObserver);
    }

    /**
     */
    public void customChartSaveTemplate(mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCustomChartSaveTemplateMethod(), responseObserver);
    }

    /**
     */
    public void customChartApplyTemplate(mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCustomChartApplyTemplateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getChartOpenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest,
                mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply>(
                  this, METHODID_CHART_OPEN)))
          .addMethod(
            getChartCloseMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest,
                mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply>(
                  this, METHODID_CHART_CLOSE)))
          .addMethod(
            getCustomChartOpenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest,
                mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply>(
                  this, METHODID_CUSTOM_CHART_OPEN)))
          .addMethod(
            getCustomChartSaveTemplateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest,
                mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply>(
                  this, METHODID_CUSTOM_CHART_SAVE_TEMPLATE)))
          .addMethod(
            getCustomChartApplyTemplateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest,
                mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply>(
                  this, METHODID_CUSTOM_CHART_APPLY_TEMPLATE)))
          .build();
    }
  }

  /**
   */
  public static final class InternalChartsStub extends io.grpc.stub.AbstractAsyncStub<InternalChartsStub> {
    private InternalChartsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InternalChartsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InternalChartsStub(channel, callOptions);
    }

    /**
     */
    public void chartOpen(mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getChartOpenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void chartClose(mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getChartCloseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void customChartOpen(mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCustomChartOpenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void customChartSaveTemplate(mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCustomChartSaveTemplateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void customChartApplyTemplate(mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCustomChartApplyTemplateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InternalChartsBlockingStub extends io.grpc.stub.AbstractBlockingStub<InternalChartsBlockingStub> {
    private InternalChartsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InternalChartsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InternalChartsBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply chartOpen(mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getChartOpenMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply chartClose(mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getChartCloseMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply customChartOpen(mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCustomChartOpenMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply customChartSaveTemplate(mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCustomChartSaveTemplateMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply customChartApplyTemplate(mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCustomChartApplyTemplateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InternalChartsFutureStub extends io.grpc.stub.AbstractFutureStub<InternalChartsFutureStub> {
    private InternalChartsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InternalChartsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InternalChartsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply> chartOpen(
        mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getChartOpenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply> chartClose(
        mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getChartCloseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply> customChartOpen(
        mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCustomChartOpenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply> customChartSaveTemplate(
        mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCustomChartSaveTemplateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply> customChartApplyTemplate(
        mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCustomChartApplyTemplateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHART_OPEN = 0;
  private static final int METHODID_CHART_CLOSE = 1;
  private static final int METHODID_CUSTOM_CHART_OPEN = 2;
  private static final int METHODID_CUSTOM_CHART_SAVE_TEMPLATE = 3;
  private static final int METHODID_CUSTOM_CHART_APPLY_TEMPLATE = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InternalChartsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InternalChartsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CHART_OPEN:
          serviceImpl.chartOpen((mt4_term_api.Mt4TermApiInternalCharts.ChartOpenRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.ChartOpenReply>) responseObserver);
          break;
        case METHODID_CHART_CLOSE:
          serviceImpl.chartClose((mt4_term_api.Mt4TermApiInternalCharts.ChartCloseRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.ChartCloseReply>) responseObserver);
          break;
        case METHODID_CUSTOM_CHART_OPEN:
          serviceImpl.customChartOpen((mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartOpenReply>) responseObserver);
          break;
        case METHODID_CUSTOM_CHART_SAVE_TEMPLATE:
          serviceImpl.customChartSaveTemplate((mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartSaveTemplateReply>) responseObserver);
          break;
        case METHODID_CUSTOM_CHART_APPLY_TEMPLATE:
          serviceImpl.customChartApplyTemplate((mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiInternalCharts.CustomChartApplyTemplateReply>) responseObserver);
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

  private static abstract class InternalChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InternalChartsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiInternalCharts.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InternalCharts");
    }
  }

  private static final class InternalChartsFileDescriptorSupplier
      extends InternalChartsBaseDescriptorSupplier {
    InternalChartsFileDescriptorSupplier() {}
  }

  private static final class InternalChartsMethodDescriptorSupplier
      extends InternalChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    InternalChartsMethodDescriptorSupplier(String methodName) {
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
      synchronized (InternalChartsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InternalChartsFileDescriptorSupplier())
              .addMethod(getChartOpenMethod())
              .addMethod(getChartCloseMethod())
              .addMethod(getCustomChartOpenMethod())
              .addMethod(getCustomChartSaveTemplateMethod())
              .addMethod(getCustomChartApplyTemplateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
