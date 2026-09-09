package mt4_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-connection.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConnectionGrpc {

  private ConnectionGrpc() {}

  public static final String SERVICE_NAME = "mt4_term_api.Connection";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectExRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectExReply> getConnectExMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectEx",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectExRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectExReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectExRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectExReply> getConnectExMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectExRequest, mt4_term_api.Mt4TermApiConnection.ConnectExReply> getConnectExMethod;
    if ((getConnectExMethod = ConnectionGrpc.getConnectExMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectExMethod = ConnectionGrpc.getConnectExMethod) == null) {
          ConnectionGrpc.getConnectExMethod = getConnectExMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectExRequest, mt4_term_api.Mt4TermApiConnection.ConnectExReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectEx"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectExRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectExReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectEx"))
              .build();
        }
      }
    }
    return getConnectExMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectReply> getConnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Connect",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectReply> getConnectMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectRequest, mt4_term_api.Mt4TermApiConnection.ConnectReply> getConnectMethod;
    if ((getConnectMethod = ConnectionGrpc.getConnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectMethod = ConnectionGrpc.getConnectMethod) == null) {
          ConnectionGrpc.getConnectMethod = getConnectMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectRequest, mt4_term_api.Mt4TermApiConnection.ConnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Connect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Connect"))
              .build();
        }
      }
    }
    return getConnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectProxyReply> getConnectProxyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectProxy",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectProxyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectProxyReply> getConnectProxyMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest, mt4_term_api.Mt4TermApiConnection.ConnectProxyReply> getConnectProxyMethod;
    if ((getConnectProxyMethod = ConnectionGrpc.getConnectProxyMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectProxyMethod = ConnectionGrpc.getConnectProxyMethod) == null) {
          ConnectionGrpc.getConnectProxyMethod = getConnectProxyMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest, mt4_term_api.Mt4TermApiConnection.ConnectProxyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectProxy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectProxyReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectProxy"))
              .build();
        }
      }
    }
    return getConnectProxyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.CheckConnectRequest,
      mt4_term_api.Mt4TermApiConnection.CheckConnectReply> getCheckConnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckConnect",
      requestType = mt4_term_api.Mt4TermApiConnection.CheckConnectRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.CheckConnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.CheckConnectRequest,
      mt4_term_api.Mt4TermApiConnection.CheckConnectReply> getCheckConnectMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.CheckConnectRequest, mt4_term_api.Mt4TermApiConnection.CheckConnectReply> getCheckConnectMethod;
    if ((getCheckConnectMethod = ConnectionGrpc.getCheckConnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getCheckConnectMethod = ConnectionGrpc.getCheckConnectMethod) == null) {
          ConnectionGrpc.getCheckConnectMethod = getCheckConnectMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.CheckConnectRequest, mt4_term_api.Mt4TermApiConnection.CheckConnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckConnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.CheckConnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.CheckConnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("CheckConnect"))
              .build();
        }
      }
    }
    return getCheckConnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectStateRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectStateReply> getConnectStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectState",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectStateRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectStateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectStateRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectStateReply> getConnectStateMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectStateRequest, mt4_term_api.Mt4TermApiConnection.ConnectStateReply> getConnectStateMethod;
    if ((getConnectStateMethod = ConnectionGrpc.getConnectStateMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectStateMethod = ConnectionGrpc.getConnectStateMethod) == null) {
          ConnectionGrpc.getConnectStateMethod = getConnectStateMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectStateRequest, mt4_term_api.Mt4TermApiConnection.ConnectStateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectStateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectStateReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectState"))
              .build();
        }
      }
    }
    return getConnectStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest,
      mt4_term_api.Mt4TermApiConnection.OnConnectStateReply> getOnConnectStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnConnectState",
      requestType = mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.OnConnectStateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest,
      mt4_term_api.Mt4TermApiConnection.OnConnectStateReply> getOnConnectStateMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest, mt4_term_api.Mt4TermApiConnection.OnConnectStateReply> getOnConnectStateMethod;
    if ((getOnConnectStateMethod = ConnectionGrpc.getOnConnectStateMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getOnConnectStateMethod = ConnectionGrpc.getOnConnectStateMethod) == null) {
          ConnectionGrpc.getOnConnectStateMethod = getOnConnectStateMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest, mt4_term_api.Mt4TermApiConnection.OnConnectStateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnConnectState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.OnConnectStateReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("OnConnectState"))
              .build();
        }
      }
    }
    return getOnConnectStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply> getConnectionStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectionStatus",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply> getConnectionStatusMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest, mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply> getConnectionStatusMethod;
    if ((getConnectionStatusMethod = ConnectionGrpc.getConnectionStatusMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectionStatusMethod = ConnectionGrpc.getConnectionStatusMethod) == null) {
          ConnectionGrpc.getConnectionStatusMethod = getConnectionStatusMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest, mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectionStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectionStatus"))
              .build();
        }
      }
    }
    return getConnectionStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.DisconnectRequest,
      mt4_term_api.Mt4TermApiConnection.DisconnectReply> getDisconnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Disconnect",
      requestType = mt4_term_api.Mt4TermApiConnection.DisconnectRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.DisconnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.DisconnectRequest,
      mt4_term_api.Mt4TermApiConnection.DisconnectReply> getDisconnectMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.DisconnectRequest, mt4_term_api.Mt4TermApiConnection.DisconnectReply> getDisconnectMethod;
    if ((getDisconnectMethod = ConnectionGrpc.getDisconnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getDisconnectMethod = ConnectionGrpc.getDisconnectMethod) == null) {
          ConnectionGrpc.getDisconnectMethod = getDisconnectMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.DisconnectRequest, mt4_term_api.Mt4TermApiConnection.DisconnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Disconnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.DisconnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.DisconnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Disconnect"))
              .build();
        }
      }
    }
    return getDisconnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ReconnectRequest,
      mt4_term_api.Mt4TermApiConnection.ReconnectReply> getReconnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reconnect",
      requestType = mt4_term_api.Mt4TermApiConnection.ReconnectRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ReconnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ReconnectRequest,
      mt4_term_api.Mt4TermApiConnection.ReconnectReply> getReconnectMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ReconnectRequest, mt4_term_api.Mt4TermApiConnection.ReconnectReply> getReconnectMethod;
    if ((getReconnectMethod = ConnectionGrpc.getReconnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getReconnectMethod = ConnectionGrpc.getReconnectMethod) == null) {
          ConnectionGrpc.getReconnectMethod = getReconnectMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ReconnectRequest, mt4_term_api.Mt4TermApiConnection.ReconnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reconnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ReconnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ReconnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Reconnect"))
              .build();
        }
      }
    }
    return getReconnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectExReply> getConnectByTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectByToken",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectExReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectExReply> getConnectByTokenMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest, mt4_term_api.Mt4TermApiConnection.ConnectExReply> getConnectByTokenMethod;
    if ((getConnectByTokenMethod = ConnectionGrpc.getConnectByTokenMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectByTokenMethod = ConnectionGrpc.getConnectByTokenMethod) == null) {
          ConnectionGrpc.getConnectByTokenMethod = getConnectByTokenMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest, mt4_term_api.Mt4TermApiConnection.ConnectExReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectByToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectExReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectByToken"))
              .build();
        }
      }
    }
    return getConnectByTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest,
      mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply> getGetBrokerServersByBrokerNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBrokerServersByBrokerName",
      requestType = mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest,
      mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply> getGetBrokerServersByBrokerNameMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest, mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply> getGetBrokerServersByBrokerNameMethod;
    if ((getGetBrokerServersByBrokerNameMethod = ConnectionGrpc.getGetBrokerServersByBrokerNameMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getGetBrokerServersByBrokerNameMethod = ConnectionGrpc.getGetBrokerServersByBrokerNameMethod) == null) {
          ConnectionGrpc.getGetBrokerServersByBrokerNameMethod = getGetBrokerServersByBrokerNameMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest, mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBrokerServersByBrokerName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("GetBrokerServersByBrokerName"))
              .build();
        }
      }
    }
    return getGetBrokerServersByBrokerNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.GetIdRequest,
      mt4_term_api.Mt4TermApiConnection.GetIdReply> getGetIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetId",
      requestType = mt4_term_api.Mt4TermApiConnection.GetIdRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.GetIdReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.GetIdRequest,
      mt4_term_api.Mt4TermApiConnection.GetIdReply> getGetIdMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.GetIdRequest, mt4_term_api.Mt4TermApiConnection.GetIdReply> getGetIdMethod;
    if ((getGetIdMethod = ConnectionGrpc.getGetIdMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getGetIdMethod = ConnectionGrpc.getGetIdMethod) == null) {
          ConnectionGrpc.getGetIdMethod = getGetIdMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.GetIdRequest, mt4_term_api.Mt4TermApiConnection.GetIdReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.GetIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.GetIdReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("GetId"))
              .build();
        }
      }
    }
    return getGetIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ScreenshotRequest,
      mt4_term_api.Mt4TermApiConnection.ScreenshotReply> getScreenshotMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Screenshot",
      requestType = mt4_term_api.Mt4TermApiConnection.ScreenshotRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ScreenshotReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ScreenshotRequest,
      mt4_term_api.Mt4TermApiConnection.ScreenshotReply> getScreenshotMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ScreenshotRequest, mt4_term_api.Mt4TermApiConnection.ScreenshotReply> getScreenshotMethod;
    if ((getScreenshotMethod = ConnectionGrpc.getScreenshotMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getScreenshotMethod = ConnectionGrpc.getScreenshotMethod) == null) {
          ConnectionGrpc.getScreenshotMethod = getScreenshotMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ScreenshotRequest, mt4_term_api.Mt4TermApiConnection.ScreenshotReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Screenshot"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ScreenshotRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ScreenshotReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Screenshot"))
              .build();
        }
      }
    }
    return getScreenshotMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> getConnectStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectStream",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> getConnectStreamMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectRequest, mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> getConnectStreamMethod;
    if ((getConnectStreamMethod = ConnectionGrpc.getConnectStreamMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectStreamMethod = ConnectionGrpc.getConnectStreamMethod) == null) {
          ConnectionGrpc.getConnectStreamMethod = getConnectStreamMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectRequest, mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectStream"))
              .build();
        }
      }
    }
    return getConnectStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectExRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> getConnectExStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectExStream",
      requestType = mt4_term_api.Mt4TermApiConnection.ConnectExRequest.class,
      responseType = mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectExRequest,
      mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> getConnectExStreamMethod() {
    io.grpc.MethodDescriptor<mt4_term_api.Mt4TermApiConnection.ConnectExRequest, mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> getConnectExStreamMethod;
    if ((getConnectExStreamMethod = ConnectionGrpc.getConnectExStreamMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectExStreamMethod = ConnectionGrpc.getConnectExStreamMethod) == null) {
          ConnectionGrpc.getConnectExStreamMethod = getConnectExStreamMethod =
              io.grpc.MethodDescriptor.<mt4_term_api.Mt4TermApiConnection.ConnectExRequest, mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectExStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectExRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectExStream"))
              .build();
        }
      }
    }
    return getConnectExStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConnectionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConnectionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConnectionStub>() {
        @java.lang.Override
        public ConnectionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConnectionStub(channel, callOptions);
        }
      };
    return ConnectionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConnectionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConnectionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConnectionBlockingStub>() {
        @java.lang.Override
        public ConnectionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConnectionBlockingStub(channel, callOptions);
        }
      };
    return ConnectionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConnectionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConnectionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConnectionFutureStub>() {
        @java.lang.Override
        public ConnectionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConnectionFutureStub(channel, callOptions);
        }
      };
    return ConnectionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ConnectionImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Creates terminal connection to the MT5 server by MT cluster name, waits for connection and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }  
     * </pre>
     */
    public void connectEx(mt4_term_api.Mt4TermApiConnection.ConnectExRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectExReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectExMethod(), responseObserver);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT4 server and returns Guid of it
     * [DefaultValues]
     * {
     *   "user": "501401178",
     *   "password": "v8gctta",
     *   "host": "mt4-demo.roboforex.com",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }
     * </pre>
     */
    public void connect(mt4_term_api.Mt4TermApiConnection.ConnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "500476959", 
     *  "password": "ehj4bod", 
     *  "host": "mt4-demo.roboforex.com", 
     *  "port": "443",
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public void connectProxy(mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectProxyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectProxyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT4 server is alive
     * </pre>
     */
    public void checkConnect(mt4_term_api.Mt4TermApiConnection.CheckConnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.CheckConnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckConnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns detailed live connection state of this terminal instance
     * </pre>
     */
    public void connectState(mt4_term_api.Mt4TermApiConnection.ConnectStateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStateReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectStateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams real-time connection state changes for specified or visible terminals
     * </pre>
     */
    public void onConnectState(mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.OnConnectStateReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnConnectStateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Deprecated alias for ConnectState
     * </pre>
     */
    public void connectionStatus(mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectionStatusMethod(), responseObserver);
    }

    /**
     * <pre>
     * Close terminal connection to MT4 server
     * </pre>
     */
    public void disconnect(mt4_term_api.Mt4TermApiConnection.DisconnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.DisconnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisconnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public void reconnect(mt4_term_api.Mt4TermApiConnection.ReconnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ReconnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReconnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Rebuild a terminal instance from a previously-saved token_details_mt4 row. The id header
     * names the terminal to bring back; every credential (User/Password/Server or Host+Port,
     * proxy settings, servers.dat bytes, PfxFile, HardwareId, Build) is read from the DB. Same
     * reply as ConnectEx so callers do not need a separate result path.
     * </pre>
     */
    public void connectByToken(mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectExReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectByTokenMethod(), responseObserver);
    }

    /**
     */
    public void getBrokerServersByBrokerName(mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBrokerServersByBrokerNameMethod(), responseObserver);
    }

    /**
     * <pre>
     * Generates a deterministic GUID from user and password.
     * The same user/password combination always produces the same GUID.
     * Use this GUID as the 'id' header for Connect and other endpoints.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck"
     * }
     * </pre>
     */
    public void getId(mt4_term_api.Mt4TermApiConnection.GetIdRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.GetIdReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIdMethod(), responseObserver);
    }

    /**
     * <pre>
     * Captures a screenshot of the terminal instance window.
     * Handled by Terminal Manager: the per-terminal in-process responder grabs the
     * MT4 window and the manager returns the image. Requires 'id' header.
     * </pre>
     */
    public void screenshot(mt4_term_api.Mt4TermApiConnection.ScreenshotRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ScreenshotReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getScreenshotMethod(), responseObserver);
    }

    /**
     * <pre>
     * Same as Connect but streams real-time progress events (incl. live Journal/
     * Experts log lines) while the connection is established.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /connect-stream interactive viewer.
     * </pre>
     */
    public void connectStream(mt4_term_api.Mt4TermApiConnection.ConnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * Same as ConnectEx but streams real-time progress events (incl. live Journal/
     * Experts log lines) while the connection is established.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /connect-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }
     * </pre>
     */
    public void connectExStream(mt4_term_api.Mt4TermApiConnection.ConnectExRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectExStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getConnectExMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectExRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectExReply>(
                  this, METHODID_CONNECT_EX)))
          .addMethod(
            getConnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectReply>(
                  this, METHODID_CONNECT)))
          .addMethod(
            getConnectProxyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectProxyReply>(
                  this, METHODID_CONNECT_PROXY)))
          .addMethod(
            getCheckConnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.CheckConnectRequest,
                mt4_term_api.Mt4TermApiConnection.CheckConnectReply>(
                  this, METHODID_CHECK_CONNECT)))
          .addMethod(
            getConnectStateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectStateRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectStateReply>(
                  this, METHODID_CONNECT_STATE)))
          .addMethod(
            getOnConnectStateMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest,
                mt4_term_api.Mt4TermApiConnection.OnConnectStateReply>(
                  this, METHODID_ON_CONNECT_STATE)))
          .addMethod(
            getConnectionStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply>(
                  this, METHODID_CONNECTION_STATUS)))
          .addMethod(
            getDisconnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.DisconnectRequest,
                mt4_term_api.Mt4TermApiConnection.DisconnectReply>(
                  this, METHODID_DISCONNECT)))
          .addMethod(
            getReconnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ReconnectRequest,
                mt4_term_api.Mt4TermApiConnection.ReconnectReply>(
                  this, METHODID_RECONNECT)))
          .addMethod(
            getConnectByTokenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectExReply>(
                  this, METHODID_CONNECT_BY_TOKEN)))
          .addMethod(
            getGetBrokerServersByBrokerNameMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest,
                mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply>(
                  this, METHODID_GET_BROKER_SERVERS_BY_BROKER_NAME)))
          .addMethod(
            getGetIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.GetIdRequest,
                mt4_term_api.Mt4TermApiConnection.GetIdReply>(
                  this, METHODID_GET_ID)))
          .addMethod(
            getScreenshotMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ScreenshotRequest,
                mt4_term_api.Mt4TermApiConnection.ScreenshotReply>(
                  this, METHODID_SCREENSHOT)))
          .addMethod(
            getConnectStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent>(
                  this, METHODID_CONNECT_STREAM)))
          .addMethod(
            getConnectExStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt4_term_api.Mt4TermApiConnection.ConnectExRequest,
                mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent>(
                  this, METHODID_CONNECT_EX_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class ConnectionStub extends io.grpc.stub.AbstractAsyncStub<ConnectionStub> {
    private ConnectionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConnectionStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT5 server by MT cluster name, waits for connection and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }  
     * </pre>
     */
    public void connectEx(mt4_term_api.Mt4TermApiConnection.ConnectExRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectExReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectExMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT4 server and returns Guid of it
     * [DefaultValues]
     * {
     *   "user": "501401178",
     *   "password": "v8gctta",
     *   "host": "mt4-demo.roboforex.com",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }
     * </pre>
     */
    public void connect(mt4_term_api.Mt4TermApiConnection.ConnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "500476959", 
     *  "password": "ehj4bod", 
     *  "host": "mt4-demo.roboforex.com", 
     *  "port": "443",
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public void connectProxy(mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectProxyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectProxyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT4 server is alive
     * </pre>
     */
    public void checkConnect(mt4_term_api.Mt4TermApiConnection.CheckConnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.CheckConnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckConnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns detailed live connection state of this terminal instance
     * </pre>
     */
    public void connectState(mt4_term_api.Mt4TermApiConnection.ConnectStateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStateReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams real-time connection state changes for specified or visible terminals
     * </pre>
     */
    public void onConnectState(mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.OnConnectStateReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnConnectStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deprecated alias for ConnectState
     * </pre>
     */
    public void connectionStatus(mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectionStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Close terminal connection to MT4 server
     * </pre>
     */
    public void disconnect(mt4_term_api.Mt4TermApiConnection.DisconnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.DisconnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisconnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public void reconnect(mt4_term_api.Mt4TermApiConnection.ReconnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ReconnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReconnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Rebuild a terminal instance from a previously-saved token_details_mt4 row. The id header
     * names the terminal to bring back; every credential (User/Password/Server or Host+Port,
     * proxy settings, servers.dat bytes, PfxFile, HardwareId, Build) is read from the DB. Same
     * reply as ConnectEx so callers do not need a separate result path.
     * </pre>
     */
    public void connectByToken(mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectExReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectByTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBrokerServersByBrokerName(mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBrokerServersByBrokerNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Generates a deterministic GUID from user and password.
     * The same user/password combination always produces the same GUID.
     * Use this GUID as the 'id' header for Connect and other endpoints.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck"
     * }
     * </pre>
     */
    public void getId(mt4_term_api.Mt4TermApiConnection.GetIdRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.GetIdReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Captures a screenshot of the terminal instance window.
     * Handled by Terminal Manager: the per-terminal in-process responder grabs the
     * MT4 window and the manager returns the image. Requires 'id' header.
     * </pre>
     */
    public void screenshot(mt4_term_api.Mt4TermApiConnection.ScreenshotRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ScreenshotReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getScreenshotMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Same as Connect but streams real-time progress events (incl. live Journal/
     * Experts log lines) while the connection is established.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /connect-stream interactive viewer.
     * </pre>
     */
    public void connectStream(mt4_term_api.Mt4TermApiConnection.ConnectRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getConnectStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Same as ConnectEx but streams real-time progress events (incl. live Journal/
     * Experts log lines) while the connection is established.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /connect-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }
     * </pre>
     */
    public void connectExStream(mt4_term_api.Mt4TermApiConnection.ConnectExRequest request,
        io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getConnectExStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConnectionBlockingStub extends io.grpc.stub.AbstractBlockingStub<ConnectionBlockingStub> {
    private ConnectionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConnectionBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT5 server by MT cluster name, waits for connection and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }  
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ConnectExReply connectEx(mt4_term_api.Mt4TermApiConnection.ConnectExRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectExMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT4 server and returns Guid of it
     * [DefaultValues]
     * {
     *   "user": "501401178",
     *   "password": "v8gctta",
     *   "host": "mt4-demo.roboforex.com",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ConnectReply connect(mt4_term_api.Mt4TermApiConnection.ConnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "500476959", 
     *  "password": "ehj4bod", 
     *  "host": "mt4-demo.roboforex.com", 
     *  "port": "443",
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ConnectProxyReply connectProxy(mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectProxyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT4 server is alive
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.CheckConnectReply checkConnect(mt4_term_api.Mt4TermApiConnection.CheckConnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckConnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns detailed live connection state of this terminal instance
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ConnectStateReply connectState(mt4_term_api.Mt4TermApiConnection.ConnectStateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectStateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams real-time connection state changes for specified or visible terminals
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiConnection.OnConnectStateReply> onConnectState(
        mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnConnectStateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Deprecated alias for ConnectState
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply connectionStatus(mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectionStatusMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Close terminal connection to MT4 server
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.DisconnectReply disconnect(mt4_term_api.Mt4TermApiConnection.DisconnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisconnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ReconnectReply reconnect(mt4_term_api.Mt4TermApiConnection.ReconnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReconnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Rebuild a terminal instance from a previously-saved token_details_mt4 row. The id header
     * names the terminal to bring back; every credential (User/Password/Server or Host+Port,
     * proxy settings, servers.dat bytes, PfxFile, HardwareId, Build) is read from the DB. Same
     * reply as ConnectEx so callers do not need a separate result path.
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ConnectExReply connectByToken(mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectByTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply getBrokerServersByBrokerName(mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBrokerServersByBrokerNameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Generates a deterministic GUID from user and password.
     * The same user/password combination always produces the same GUID.
     * Use this GUID as the 'id' header for Connect and other endpoints.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck"
     * }
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.GetIdReply getId(mt4_term_api.Mt4TermApiConnection.GetIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetIdMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Captures a screenshot of the terminal instance window.
     * Handled by Terminal Manager: the per-terminal in-process responder grabs the
     * MT4 window and the manager returns the image. Requires 'id' header.
     * </pre>
     */
    public mt4_term_api.Mt4TermApiConnection.ScreenshotReply screenshot(mt4_term_api.Mt4TermApiConnection.ScreenshotRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getScreenshotMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Same as Connect but streams real-time progress events (incl. live Journal/
     * Experts log lines) while the connection is established.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /connect-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> connectStream(
        mt4_term_api.Mt4TermApiConnection.ConnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getConnectStreamMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Same as ConnectEx but streams real-time progress events (incl. live Journal/
     * Experts log lines) while the connection is established.
     * Requires 'id' header — use GetId to generate.
     * Swagger does not support streaming — use /connect-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }
     * </pre>
     */
    public java.util.Iterator<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent> connectExStream(
        mt4_term_api.Mt4TermApiConnection.ConnectExRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getConnectExStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConnectionFutureStub extends io.grpc.stub.AbstractFutureStub<ConnectionFutureStub> {
    private ConnectionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConnectionFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT5 server by MT cluster name, waits for connection and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck",
     *   "mtClusterName": "FreshForex-Demo"
     * }  
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ConnectExReply> connectEx(
        mt4_term_api.Mt4TermApiConnection.ConnectExRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectExMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT4 server and returns Guid of it
     * [DefaultValues]
     * {
     *   "user": "501401178",
     *   "password": "v8gctta",
     *   "host": "mt4-demo.roboforex.com",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ConnectReply> connect(
        mt4_term_api.Mt4TermApiConnection.ConnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "500476959", 
     *  "password": "ehj4bod", 
     *  "host": "mt4-demo.roboforex.com", 
     *  "port": "443",
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ConnectProxyReply> connectProxy(
        mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectProxyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT4 server is alive
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.CheckConnectReply> checkConnect(
        mt4_term_api.Mt4TermApiConnection.CheckConnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckConnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns detailed live connection state of this terminal instance
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ConnectStateReply> connectState(
        mt4_term_api.Mt4TermApiConnection.ConnectStateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectStateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Deprecated alias for ConnectState
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply> connectionStatus(
        mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectionStatusMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Close terminal connection to MT4 server
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.DisconnectReply> disconnect(
        mt4_term_api.Mt4TermApiConnection.DisconnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisconnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ReconnectReply> reconnect(
        mt4_term_api.Mt4TermApiConnection.ReconnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReconnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Rebuild a terminal instance from a previously-saved token_details_mt4 row. The id header
     * names the terminal to bring back; every credential (User/Password/Server or Host+Port,
     * proxy settings, servers.dat bytes, PfxFile, HardwareId, Build) is read from the DB. Same
     * reply as ConnectEx so callers do not need a separate result path.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ConnectExReply> connectByToken(
        mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectByTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply> getBrokerServersByBrokerName(
        mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBrokerServersByBrokerNameMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Generates a deterministic GUID from user and password.
     * The same user/password combination always produces the same GUID.
     * Use this GUID as the 'id' header for Connect and other endpoints.
     * [DefaultValues]
     * {
     *   "user": "1124213727",
     *   "password": "1tjvjck"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.GetIdReply> getId(
        mt4_term_api.Mt4TermApiConnection.GetIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIdMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Captures a screenshot of the terminal instance window.
     * Handled by Terminal Manager: the per-terminal in-process responder grabs the
     * MT4 window and the manager returns the image. Requires 'id' header.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt4_term_api.Mt4TermApiConnection.ScreenshotReply> screenshot(
        mt4_term_api.Mt4TermApiConnection.ScreenshotRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getScreenshotMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONNECT_EX = 0;
  private static final int METHODID_CONNECT = 1;
  private static final int METHODID_CONNECT_PROXY = 2;
  private static final int METHODID_CHECK_CONNECT = 3;
  private static final int METHODID_CONNECT_STATE = 4;
  private static final int METHODID_ON_CONNECT_STATE = 5;
  private static final int METHODID_CONNECTION_STATUS = 6;
  private static final int METHODID_DISCONNECT = 7;
  private static final int METHODID_RECONNECT = 8;
  private static final int METHODID_CONNECT_BY_TOKEN = 9;
  private static final int METHODID_GET_BROKER_SERVERS_BY_BROKER_NAME = 10;
  private static final int METHODID_GET_ID = 11;
  private static final int METHODID_SCREENSHOT = 12;
  private static final int METHODID_CONNECT_STREAM = 13;
  private static final int METHODID_CONNECT_EX_STREAM = 14;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConnectionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConnectionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONNECT_EX:
          serviceImpl.connectEx((mt4_term_api.Mt4TermApiConnection.ConnectExRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectExReply>) responseObserver);
          break;
        case METHODID_CONNECT:
          serviceImpl.connect((mt4_term_api.Mt4TermApiConnection.ConnectRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectReply>) responseObserver);
          break;
        case METHODID_CONNECT_PROXY:
          serviceImpl.connectProxy((mt4_term_api.Mt4TermApiConnection.ConnectProxyRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectProxyReply>) responseObserver);
          break;
        case METHODID_CHECK_CONNECT:
          serviceImpl.checkConnect((mt4_term_api.Mt4TermApiConnection.CheckConnectRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.CheckConnectReply>) responseObserver);
          break;
        case METHODID_CONNECT_STATE:
          serviceImpl.connectState((mt4_term_api.Mt4TermApiConnection.ConnectStateRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStateReply>) responseObserver);
          break;
        case METHODID_ON_CONNECT_STATE:
          serviceImpl.onConnectState((mt4_term_api.Mt4TermApiConnection.OnConnectStateRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.OnConnectStateReply>) responseObserver);
          break;
        case METHODID_CONNECTION_STATUS:
          serviceImpl.connectionStatus((mt4_term_api.Mt4TermApiConnection.ConnectionStatusRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectionStatusReply>) responseObserver);
          break;
        case METHODID_DISCONNECT:
          serviceImpl.disconnect((mt4_term_api.Mt4TermApiConnection.DisconnectRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.DisconnectReply>) responseObserver);
          break;
        case METHODID_RECONNECT:
          serviceImpl.reconnect((mt4_term_api.Mt4TermApiConnection.ReconnectRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ReconnectReply>) responseObserver);
          break;
        case METHODID_CONNECT_BY_TOKEN:
          serviceImpl.connectByToken((mt4_term_api.Mt4TermApiConnection.ConnectByTokenRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectExReply>) responseObserver);
          break;
        case METHODID_GET_BROKER_SERVERS_BY_BROKER_NAME:
          serviceImpl.getBrokerServersByBrokerName((mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.GetBrokerServersByBrokerNameReply>) responseObserver);
          break;
        case METHODID_GET_ID:
          serviceImpl.getId((mt4_term_api.Mt4TermApiConnection.GetIdRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.GetIdReply>) responseObserver);
          break;
        case METHODID_SCREENSHOT:
          serviceImpl.screenshot((mt4_term_api.Mt4TermApiConnection.ScreenshotRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ScreenshotReply>) responseObserver);
          break;
        case METHODID_CONNECT_STREAM:
          serviceImpl.connectStream((mt4_term_api.Mt4TermApiConnection.ConnectRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent>) responseObserver);
          break;
        case METHODID_CONNECT_EX_STREAM:
          serviceImpl.connectExStream((mt4_term_api.Mt4TermApiConnection.ConnectExRequest) request,
              (io.grpc.stub.StreamObserver<mt4_term_api.Mt4TermApiConnection.ConnectStreamEvent>) responseObserver);
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

  private static abstract class ConnectionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConnectionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt4_term_api.Mt4TermApiConnection.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Connection");
    }
  }

  private static final class ConnectionFileDescriptorSupplier
      extends ConnectionBaseDescriptorSupplier {
    ConnectionFileDescriptorSupplier() {}
  }

  private static final class ConnectionMethodDescriptorSupplier
      extends ConnectionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConnectionMethodDescriptorSupplier(String methodName) {
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
      synchronized (ConnectionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConnectionFileDescriptorSupplier())
              .addMethod(getConnectExMethod())
              .addMethod(getConnectMethod())
              .addMethod(getConnectProxyMethod())
              .addMethod(getCheckConnectMethod())
              .addMethod(getConnectStateMethod())
              .addMethod(getOnConnectStateMethod())
              .addMethod(getConnectionStatusMethod())
              .addMethod(getDisconnectMethod())
              .addMethod(getReconnectMethod())
              .addMethod(getConnectByTokenMethod())
              .addMethod(getGetBrokerServersByBrokerNameMethod())
              .addMethod(getGetIdMethod())
              .addMethod(getScreenshotMethod())
              .addMethod(getConnectStreamMethod())
              .addMethod(getConnectExStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
