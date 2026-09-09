package mrpc_admin;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================
 * Admin / diagnostics service (terminal-agnostic — identical to the mt5 copy).
 * Auth via admin_key (matched against the ADMIN_KEY env setting).
 * HTTP-transcoded for the /admin HTML page + cross-pod fan-out, but HIDDEN from
 * Swagger (excluded in SwaggerGen DocInclusionPredicate in Program.cs).
 * NOTE: this file exists ONLY so the mt4 client libraries (create-grpc-libs,
 * proto/mt4) include the admin API, matching mt5. The Terminal Manager binary
 * compiles the single shared mrpc_admin proto from proto/mt5 (see the csproj) —
 * do NOT add this file to the csproj or mrpc_admin.* would be defined twice.
 * ============================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt4-term-api-admin.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdminApiGrpc {

  private AdminApiGrpc() {}

  public static final String SERVICE_NAME = "mrpc_admin.AdminApi";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply> getActiveTerminalsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ActiveTerminals",
      requestType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply> getActiveTerminalsMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply> getActiveTerminalsMethod;
    if ((getActiveTerminalsMethod = AdminApiGrpc.getActiveTerminalsMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getActiveTerminalsMethod = AdminApiGrpc.getActiveTerminalsMethod) == null) {
          AdminApiGrpc.getActiveTerminalsMethod = getActiveTerminalsMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ActiveTerminals"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("ActiveTerminals"))
              .build();
        }
      }
    }
    return getActiveTerminalsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply> getActiveTerminalsClusterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ActiveTerminalsCluster",
      requestType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply> getActiveTerminalsClusterMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply> getActiveTerminalsClusterMethod;
    if ((getActiveTerminalsClusterMethod = AdminApiGrpc.getActiveTerminalsClusterMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getActiveTerminalsClusterMethod = AdminApiGrpc.getActiveTerminalsClusterMethod) == null) {
          AdminApiGrpc.getActiveTerminalsClusterMethod = getActiveTerminalsClusterMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ActiveTerminalsCluster"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("ActiveTerminalsCluster"))
              .build();
        }
      }
    }
    return getActiveTerminalsClusterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.SystemUsageReply> getSystemUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SystemUsage",
      requestType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.SystemUsageReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.SystemUsageReply> getSystemUsageMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.SystemUsageReply> getSystemUsageMethod;
    if ((getSystemUsageMethod = AdminApiGrpc.getSystemUsageMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getSystemUsageMethod = AdminApiGrpc.getSystemUsageMethod) == null) {
          AdminApiGrpc.getSystemUsageMethod = getSystemUsageMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.SystemUsageReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SystemUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.SystemUsageReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("SystemUsage"))
              .build();
        }
      }
    }
    return getSystemUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply> getListLogFilesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListLogFiles",
      requestType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply> getListLogFilesMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply> getListLogFilesMethod;
    if ((getListLogFilesMethod = AdminApiGrpc.getListLogFilesMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getListLogFilesMethod = AdminApiGrpc.getListLogFilesMethod) == null) {
          AdminApiGrpc.getListLogFilesMethod = getListLogFilesMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListLogFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("ListLogFiles"))
              .build();
        }
      }
    }
    return getListLogFilesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest,
      mrpc_admin.Mt4TermApiAdmin.GetLogFileReply> getGetLogFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLogFile",
      requestType = mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.GetLogFileReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest,
      mrpc_admin.Mt4TermApiAdmin.GetLogFileReply> getGetLogFileMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest, mrpc_admin.Mt4TermApiAdmin.GetLogFileReply> getGetLogFileMethod;
    if ((getGetLogFileMethod = AdminApiGrpc.getGetLogFileMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getGetLogFileMethod = AdminApiGrpc.getGetLogFileMethod) == null) {
          AdminApiGrpc.getGetLogFileMethod = getGetLogFileMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest, mrpc_admin.Mt4TermApiAdmin.GetLogFileReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLogFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetLogFileReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("GetLogFile"))
              .build();
        }
      }
    }
    return getGetLogFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest,
      mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply> getGetEventLogEntriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEventLogEntries",
      requestType = mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest,
      mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply> getGetEventLogEntriesMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest, mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply> getGetEventLogEntriesMethod;
    if ((getGetEventLogEntriesMethod = AdminApiGrpc.getGetEventLogEntriesMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getGetEventLogEntriesMethod = AdminApiGrpc.getGetEventLogEntriesMethod) == null) {
          AdminApiGrpc.getGetEventLogEntriesMethod = getGetEventLogEntriesMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest, mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEventLogEntries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("GetEventLogEntries"))
              .build();
        }
      }
    }
    return getGetEventLogEntriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest,
      mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> getCaptureSessionScreenshotMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CaptureSessionScreenshot",
      requestType = mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest,
      mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> getCaptureSessionScreenshotMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest, mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> getCaptureSessionScreenshotMethod;
    if ((getCaptureSessionScreenshotMethod = AdminApiGrpc.getCaptureSessionScreenshotMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getCaptureSessionScreenshotMethod = AdminApiGrpc.getCaptureSessionScreenshotMethod) == null) {
          AdminApiGrpc.getCaptureSessionScreenshotMethod = getCaptureSessionScreenshotMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest, mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CaptureSessionScreenshot"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("CaptureSessionScreenshot"))
              .build();
        }
      }
    }
    return getCaptureSessionScreenshotMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest,
      mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> getCaptureSessionScreenshotOnPodMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CaptureSessionScreenshotOnPod",
      requestType = mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest,
      mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> getCaptureSessionScreenshotOnPodMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest, mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> getCaptureSessionScreenshotOnPodMethod;
    if ((getCaptureSessionScreenshotOnPodMethod = AdminApiGrpc.getCaptureSessionScreenshotOnPodMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getCaptureSessionScreenshotOnPodMethod = AdminApiGrpc.getCaptureSessionScreenshotOnPodMethod) == null) {
          AdminApiGrpc.getCaptureSessionScreenshotOnPodMethod = getCaptureSessionScreenshotOnPodMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest, mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CaptureSessionScreenshotOnPod"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("CaptureSessionScreenshotOnPod"))
              .build();
        }
      }
    }
    return getCaptureSessionScreenshotOnPodMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply> getRefreshMrpcRestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RefreshMrpcRest",
      requestType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply> getRefreshMrpcRestMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply> getRefreshMrpcRestMethod;
    if ((getRefreshMrpcRestMethod = AdminApiGrpc.getRefreshMrpcRestMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getRefreshMrpcRestMethod = AdminApiGrpc.getRefreshMrpcRestMethod) == null) {
          AdminApiGrpc.getRefreshMrpcRestMethod = getRefreshMrpcRestMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RefreshMrpcRest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("RefreshMrpcRest"))
              .build();
        }
      }
    }
    return getRefreshMrpcRestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.VersionRequest,
      mrpc_admin.Mt4TermApiAdmin.VersionReply> getGetVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVersion",
      requestType = mrpc_admin.Mt4TermApiAdmin.VersionRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.VersionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.VersionRequest,
      mrpc_admin.Mt4TermApiAdmin.VersionReply> getGetVersionMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.VersionRequest, mrpc_admin.Mt4TermApiAdmin.VersionReply> getGetVersionMethod;
    if ((getGetVersionMethod = AdminApiGrpc.getGetVersionMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getGetVersionMethod = AdminApiGrpc.getGetVersionMethod) == null) {
          AdminApiGrpc.getGetVersionMethod = getGetVersionMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.VersionRequest, mrpc_admin.Mt4TermApiAdmin.VersionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.VersionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.VersionReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("GetVersion"))
              .build();
        }
      }
    }
    return getGetVersionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest,
      mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply> getGetAllLogsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllLogs",
      requestType = mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest,
      mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply> getGetAllLogsMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest, mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply> getGetAllLogsMethod;
    if ((getGetAllLogsMethod = AdminApiGrpc.getGetAllLogsMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getGetAllLogsMethod = AdminApiGrpc.getGetAllLogsMethod) == null) {
          AdminApiGrpc.getGetAllLogsMethod = getGetAllLogsMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest, mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllLogs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("GetAllLogs"))
              .build();
        }
      }
    }
    return getGetAllLogsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest,
      mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply> getGetSessionRestoreLogsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSessionRestoreLogs",
      requestType = mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest,
      mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply> getGetSessionRestoreLogsMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest, mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply> getGetSessionRestoreLogsMethod;
    if ((getGetSessionRestoreLogsMethod = AdminApiGrpc.getGetSessionRestoreLogsMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getGetSessionRestoreLogsMethod = AdminApiGrpc.getGetSessionRestoreLogsMethod) == null) {
          AdminApiGrpc.getGetSessionRestoreLogsMethod = getGetSessionRestoreLogsMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest, mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSessionRestoreLogs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("GetSessionRestoreLogs"))
              .build();
        }
      }
    }
    return getGetSessionRestoreLogsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply> getGetSessionRestoreStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSessionRestoreStatus",
      requestType = mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.class,
      responseType = mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
      mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply> getGetSessionRestoreStatusMethod() {
    io.grpc.MethodDescriptor<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply> getGetSessionRestoreStatusMethod;
    if ((getGetSessionRestoreStatusMethod = AdminApiGrpc.getGetSessionRestoreStatusMethod) == null) {
      synchronized (AdminApiGrpc.class) {
        if ((getGetSessionRestoreStatusMethod = AdminApiGrpc.getGetSessionRestoreStatusMethod) == null) {
          AdminApiGrpc.getGetSessionRestoreStatusMethod = getGetSessionRestoreStatusMethod =
              io.grpc.MethodDescriptor.<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest, mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSessionRestoreStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply.getDefaultInstance()))
              .setSchemaDescriptor(new AdminApiMethodDescriptorSupplier("GetSessionRestoreStatus"))
              .build();
        }
      }
    }
    return getGetSessionRestoreStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminApiStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminApiStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminApiStub>() {
        @java.lang.Override
        public AdminApiStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminApiStub(channel, callOptions);
        }
      };
    return AdminApiStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminApiBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminApiBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminApiBlockingStub>() {
        @java.lang.Override
        public AdminApiBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminApiBlockingStub(channel, callOptions);
        }
      };
    return AdminApiBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminApiFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminApiFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminApiFutureStub>() {
        @java.lang.Override
        public AdminApiFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminApiFutureStub(channel, callOptions);
        }
      };
    return AdminApiFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================
   * Admin / diagnostics service (terminal-agnostic — identical to the mt5 copy).
   * Auth via admin_key (matched against the ADMIN_KEY env setting).
   * HTTP-transcoded for the /admin HTML page + cross-pod fan-out, but HIDDEN from
   * Swagger (excluded in SwaggerGen DocInclusionPredicate in Program.cs).
   * NOTE: this file exists ONLY so the mt4 client libraries (create-grpc-libs,
   * proto/mt4) include the admin API, matching mt5. The Terminal Manager binary
   * compiles the single shared mrpc_admin proto from proto/mt5 (see the csproj) —
   * do NOT add this file to the csproj or mrpc_admin.* would be defined twice.
   * ============================================================
   * </pre>
   */
  public static abstract class AdminApiImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Active terminals running on THIS pod.
     * </pre>
     */
    public void activeTerminals(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getActiveTerminalsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Active terminals across ALL pods of this StatefulSet/Deployment.
     * Lists peer pods via the Kubernetes API, then calls ActiveTerminals on each.
     * </pre>
     */
    public void activeTerminalsCluster(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getActiveTerminalsClusterMethod(), responseObserver);
    }

    /**
     * <pre>
     * Whole-machine CPU % and physical RAM (current + short history) for THIS pod.
     * </pre>
     */
    public void systemUsage(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.SystemUsageReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSystemUsageMethod(), responseObserver);
    }

    /**
     * <pre>
     * Lists the diagnostic log files on &#92;&#92;host.lan&#92;Data&#92;logs (mrpc.log, startup.log,
     * install.log, boot-diag.log, and any future additions) - the exact files the container's
     * postStart hook tails into "kubectl logs", readable here without cluster/kubectl access.
     * </pre>
     */
    public void listLogFiles(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListLogFilesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Content of one log file from &#92;&#92;host.lan&#92;Data&#92;logs (see ListLogFiles). Truncated to the
     * LAST max_bytes bytes if larger (0 = server default, 256 KiB).
     * </pre>
     */
    public void getLogFile(mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetLogFileReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLogFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * On-demand dump of recent Windows Event Log entries (Service Control Manager events,
     * shutdown/restart events, Application errors) - the same data boot-diag.bat captures on a
     * service state change, available here without waiting for that trigger.
     * </pre>
     */
    public void getEventLogEntries(mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEventLogEntriesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Screenshot of a Windows interactive session's whole desktop - a specific
     * MrpcTerminalUser&lt;N&gt; session, or (if session_user_name is empty) the pod's main/autologon
     * session, the one startup.bat runs in.
     * </pre>
     */
    public void captureSessionScreenshot(mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCaptureSessionScreenshotMethod(), responseObserver);
    }

    /**
     * <pre>
     * Same as CaptureSessionScreenshot, but fanned out from THIS pod to a DIFFERENT pod by IP
     * (mirroring the ActiveTerminalsCluster fan-out) - a browser can only reach the pod that is
     * currently serving it, not other pods' ClusterIPs, so the cluster admin view asks whichever
     * pod it's connected to relay the request.
     * </pre>
     */
    public void captureSessionScreenshotOnPod(mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCaptureSessionScreenshotOnPodMethod(), responseObserver);
    }

    /**
     * <pre>
     * Runs, in-process (LocalSystem, no guest logon/SSH needed), the same refresh
     * startup.bat performs on a successful ONLOGON boot: robocopy published-app (including
     * mrpc-rest) from the host share, then create/start MrpcRestService. Exists because
     * ONLOGON is unreliable (autologon-dependent) - this lets a stuck deploy be repaired
     * through the Terminal Manager's own already-running gRPC/HTTP API instead of waiting
     * on that trigger or requiring interactive guest access.
     * </pre>
     */
    public void refreshMrpcRest(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRefreshMrpcRestMethod(), responseObserver);
    }

    /**
     * <pre>
     * Build/version identity of the Terminal Manager running on THIS pod, so a deploy can be
     * VERIFIED rather than guessed. No admin_key required - it exposes no secrets. mrpc-rest
     * transcodes GET /version-tm onto this (its own build is at GET /version).
     * </pre>
     */
    public void getVersion(mrpc_admin.Mt4TermApiAdmin.VersionRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.VersionReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVersionMethod(), responseObserver);
    }

    /**
     * <pre>
     * One-shot bundle of EVERY diagnostic log available on this pod: every *.log file on the
     * shared Data folder AND in C:&#92;OEM, plus the Windows Application + System event logs. See the
     * mt5 admin proto for the canonical definition (this copy exists for the mt4 client libs).
     * </pre>
     */
    public void getAllLogs(mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllLogsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Session restore logs for the latest startup sequence on a pod (stored in MongoDB session_restore_logs).
     * </pre>
     */
    public void getSessionRestoreLogs(mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSessionRestoreLogsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Session restore watcher status (terminals loaded, queue count, state, diagnostics) for THIS pod.
     * </pre>
     */
    public void getSessionRestoreStatus(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSessionRestoreStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getActiveTerminalsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply>(
                  this, METHODID_ACTIVE_TERMINALS)))
          .addMethod(
            getActiveTerminalsClusterMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply>(
                  this, METHODID_ACTIVE_TERMINALS_CLUSTER)))
          .addMethod(
            getSystemUsageMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
                mrpc_admin.Mt4TermApiAdmin.SystemUsageReply>(
                  this, METHODID_SYSTEM_USAGE)))
          .addMethod(
            getListLogFilesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
                mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply>(
                  this, METHODID_LIST_LOG_FILES)))
          .addMethod(
            getGetLogFileMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest,
                mrpc_admin.Mt4TermApiAdmin.GetLogFileReply>(
                  this, METHODID_GET_LOG_FILE)))
          .addMethod(
            getGetEventLogEntriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest,
                mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply>(
                  this, METHODID_GET_EVENT_LOG_ENTRIES)))
          .addMethod(
            getCaptureSessionScreenshotMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest,
                mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply>(
                  this, METHODID_CAPTURE_SESSION_SCREENSHOT)))
          .addMethod(
            getCaptureSessionScreenshotOnPodMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest,
                mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply>(
                  this, METHODID_CAPTURE_SESSION_SCREENSHOT_ON_POD)))
          .addMethod(
            getRefreshMrpcRestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
                mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply>(
                  this, METHODID_REFRESH_MRPC_REST)))
          .addMethod(
            getGetVersionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.VersionRequest,
                mrpc_admin.Mt4TermApiAdmin.VersionReply>(
                  this, METHODID_GET_VERSION)))
          .addMethod(
            getGetAllLogsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest,
                mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply>(
                  this, METHODID_GET_ALL_LOGS)))
          .addMethod(
            getGetSessionRestoreLogsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest,
                mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply>(
                  this, METHODID_GET_SESSION_RESTORE_LOGS)))
          .addMethod(
            getGetSessionRestoreStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest,
                mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply>(
                  this, METHODID_GET_SESSION_RESTORE_STATUS)))
          .build();
    }
  }

  /**
   * <pre>
   * ============================================================
   * Admin / diagnostics service (terminal-agnostic — identical to the mt5 copy).
   * Auth via admin_key (matched against the ADMIN_KEY env setting).
   * HTTP-transcoded for the /admin HTML page + cross-pod fan-out, but HIDDEN from
   * Swagger (excluded in SwaggerGen DocInclusionPredicate in Program.cs).
   * NOTE: this file exists ONLY so the mt4 client libraries (create-grpc-libs,
   * proto/mt4) include the admin API, matching mt5. The Terminal Manager binary
   * compiles the single shared mrpc_admin proto from proto/mt5 (see the csproj) —
   * do NOT add this file to the csproj or mrpc_admin.* would be defined twice.
   * ============================================================
   * </pre>
   */
  public static final class AdminApiStub extends io.grpc.stub.AbstractAsyncStub<AdminApiStub> {
    private AdminApiStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminApiStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminApiStub(channel, callOptions);
    }

    /**
     * <pre>
     * Active terminals running on THIS pod.
     * </pre>
     */
    public void activeTerminals(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getActiveTerminalsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Active terminals across ALL pods of this StatefulSet/Deployment.
     * Lists peer pods via the Kubernetes API, then calls ActiveTerminals on each.
     * </pre>
     */
    public void activeTerminalsCluster(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getActiveTerminalsClusterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Whole-machine CPU % and physical RAM (current + short history) for THIS pod.
     * </pre>
     */
    public void systemUsage(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.SystemUsageReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSystemUsageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists the diagnostic log files on &#92;&#92;host.lan&#92;Data&#92;logs (mrpc.log, startup.log,
     * install.log, boot-diag.log, and any future additions) - the exact files the container's
     * postStart hook tails into "kubectl logs", readable here without cluster/kubectl access.
     * </pre>
     */
    public void listLogFiles(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListLogFilesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Content of one log file from &#92;&#92;host.lan&#92;Data&#92;logs (see ListLogFiles). Truncated to the
     * LAST max_bytes bytes if larger (0 = server default, 256 KiB).
     * </pre>
     */
    public void getLogFile(mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetLogFileReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLogFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * On-demand dump of recent Windows Event Log entries (Service Control Manager events,
     * shutdown/restart events, Application errors) - the same data boot-diag.bat captures on a
     * service state change, available here without waiting for that trigger.
     * </pre>
     */
    public void getEventLogEntries(mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEventLogEntriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Screenshot of a Windows interactive session's whole desktop - a specific
     * MrpcTerminalUser&lt;N&gt; session, or (if session_user_name is empty) the pod's main/autologon
     * session, the one startup.bat runs in.
     * </pre>
     */
    public void captureSessionScreenshot(mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCaptureSessionScreenshotMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Same as CaptureSessionScreenshot, but fanned out from THIS pod to a DIFFERENT pod by IP
     * (mirroring the ActiveTerminalsCluster fan-out) - a browser can only reach the pod that is
     * currently serving it, not other pods' ClusterIPs, so the cluster admin view asks whichever
     * pod it's connected to relay the request.
     * </pre>
     */
    public void captureSessionScreenshotOnPod(mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCaptureSessionScreenshotOnPodMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Runs, in-process (LocalSystem, no guest logon/SSH needed), the same refresh
     * startup.bat performs on a successful ONLOGON boot: robocopy published-app (including
     * mrpc-rest) from the host share, then create/start MrpcRestService. Exists because
     * ONLOGON is unreliable (autologon-dependent) - this lets a stuck deploy be repaired
     * through the Terminal Manager's own already-running gRPC/HTTP API instead of waiting
     * on that trigger or requiring interactive guest access.
     * </pre>
     */
    public void refreshMrpcRest(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRefreshMrpcRestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Build/version identity of the Terminal Manager running on THIS pod, so a deploy can be
     * VERIFIED rather than guessed. No admin_key required - it exposes no secrets. mrpc-rest
     * transcodes GET /version-tm onto this (its own build is at GET /version).
     * </pre>
     */
    public void getVersion(mrpc_admin.Mt4TermApiAdmin.VersionRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.VersionReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVersionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * One-shot bundle of EVERY diagnostic log available on this pod: every *.log file on the
     * shared Data folder AND in C:&#92;OEM, plus the Windows Application + System event logs. See the
     * mt5 admin proto for the canonical definition (this copy exists for the mt4 client libs).
     * </pre>
     */
    public void getAllLogs(mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllLogsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Session restore logs for the latest startup sequence on a pod (stored in MongoDB session_restore_logs).
     * </pre>
     */
    public void getSessionRestoreLogs(mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSessionRestoreLogsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Session restore watcher status (terminals loaded, queue count, state, diagnostics) for THIS pod.
     * </pre>
     */
    public void getSessionRestoreStatus(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request,
        io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSessionRestoreStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ============================================================
   * Admin / diagnostics service (terminal-agnostic — identical to the mt5 copy).
   * Auth via admin_key (matched against the ADMIN_KEY env setting).
   * HTTP-transcoded for the /admin HTML page + cross-pod fan-out, but HIDDEN from
   * Swagger (excluded in SwaggerGen DocInclusionPredicate in Program.cs).
   * NOTE: this file exists ONLY so the mt4 client libraries (create-grpc-libs,
   * proto/mt4) include the admin API, matching mt5. The Terminal Manager binary
   * compiles the single shared mrpc_admin proto from proto/mt5 (see the csproj) —
   * do NOT add this file to the csproj or mrpc_admin.* would be defined twice.
   * ============================================================
   * </pre>
   */
  public static final class AdminApiBlockingStub extends io.grpc.stub.AbstractBlockingStub<AdminApiBlockingStub> {
    private AdminApiBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminApiBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminApiBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Active terminals running on THIS pod.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply activeTerminals(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getActiveTerminalsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Active terminals across ALL pods of this StatefulSet/Deployment.
     * Lists peer pods via the Kubernetes API, then calls ActiveTerminals on each.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply activeTerminalsCluster(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getActiveTerminalsClusterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Whole-machine CPU % and physical RAM (current + short history) for THIS pod.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.SystemUsageReply systemUsage(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSystemUsageMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists the diagnostic log files on &#92;&#92;host.lan&#92;Data&#92;logs (mrpc.log, startup.log,
     * install.log, boot-diag.log, and any future additions) - the exact files the container's
     * postStart hook tails into "kubectl logs", readable here without cluster/kubectl access.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply listLogFiles(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListLogFilesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Content of one log file from &#92;&#92;host.lan&#92;Data&#92;logs (see ListLogFiles). Truncated to the
     * LAST max_bytes bytes if larger (0 = server default, 256 KiB).
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.GetLogFileReply getLogFile(mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLogFileMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * On-demand dump of recent Windows Event Log entries (Service Control Manager events,
     * shutdown/restart events, Application errors) - the same data boot-diag.bat captures on a
     * service state change, available here without waiting for that trigger.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply getEventLogEntries(mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEventLogEntriesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Screenshot of a Windows interactive session's whole desktop - a specific
     * MrpcTerminalUser&lt;N&gt; session, or (if session_user_name is empty) the pod's main/autologon
     * session, the one startup.bat runs in.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply captureSessionScreenshot(mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCaptureSessionScreenshotMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Same as CaptureSessionScreenshot, but fanned out from THIS pod to a DIFFERENT pod by IP
     * (mirroring the ActiveTerminalsCluster fan-out) - a browser can only reach the pod that is
     * currently serving it, not other pods' ClusterIPs, so the cluster admin view asks whichever
     * pod it's connected to relay the request.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply captureSessionScreenshotOnPod(mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCaptureSessionScreenshotOnPodMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Runs, in-process (LocalSystem, no guest logon/SSH needed), the same refresh
     * startup.bat performs on a successful ONLOGON boot: robocopy published-app (including
     * mrpc-rest) from the host share, then create/start MrpcRestService. Exists because
     * ONLOGON is unreliable (autologon-dependent) - this lets a stuck deploy be repaired
     * through the Terminal Manager's own already-running gRPC/HTTP API instead of waiting
     * on that trigger or requiring interactive guest access.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply refreshMrpcRest(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRefreshMrpcRestMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Build/version identity of the Terminal Manager running on THIS pod, so a deploy can be
     * VERIFIED rather than guessed. No admin_key required - it exposes no secrets. mrpc-rest
     * transcodes GET /version-tm onto this (its own build is at GET /version).
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.VersionReply getVersion(mrpc_admin.Mt4TermApiAdmin.VersionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVersionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * One-shot bundle of EVERY diagnostic log available on this pod: every *.log file on the
     * shared Data folder AND in C:&#92;OEM, plus the Windows Application + System event logs. See the
     * mt5 admin proto for the canonical definition (this copy exists for the mt4 client libs).
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply getAllLogs(mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllLogsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Session restore logs for the latest startup sequence on a pod (stored in MongoDB session_restore_logs).
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply getSessionRestoreLogs(mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSessionRestoreLogsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Session restore watcher status (terminals loaded, queue count, state, diagnostics) for THIS pod.
     * </pre>
     */
    public mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply getSessionRestoreStatus(mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSessionRestoreStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ============================================================
   * Admin / diagnostics service (terminal-agnostic — identical to the mt5 copy).
   * Auth via admin_key (matched against the ADMIN_KEY env setting).
   * HTTP-transcoded for the /admin HTML page + cross-pod fan-out, but HIDDEN from
   * Swagger (excluded in SwaggerGen DocInclusionPredicate in Program.cs).
   * NOTE: this file exists ONLY so the mt4 client libraries (create-grpc-libs,
   * proto/mt4) include the admin API, matching mt5. The Terminal Manager binary
   * compiles the single shared mrpc_admin proto from proto/mt5 (see the csproj) —
   * do NOT add this file to the csproj or mrpc_admin.* would be defined twice.
   * ============================================================
   * </pre>
   */
  public static final class AdminApiFutureStub extends io.grpc.stub.AbstractFutureStub<AdminApiFutureStub> {
    private AdminApiFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminApiFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminApiFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Active terminals running on THIS pod.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply> activeTerminals(
        mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getActiveTerminalsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Active terminals across ALL pods of this StatefulSet/Deployment.
     * Lists peer pods via the Kubernetes API, then calls ActiveTerminals on each.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply> activeTerminalsCluster(
        mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getActiveTerminalsClusterMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Whole-machine CPU % and physical RAM (current + short history) for THIS pod.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.SystemUsageReply> systemUsage(
        mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSystemUsageMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists the diagnostic log files on &#92;&#92;host.lan&#92;Data&#92;logs (mrpc.log, startup.log,
     * install.log, boot-diag.log, and any future additions) - the exact files the container's
     * postStart hook tails into "kubectl logs", readable here without cluster/kubectl access.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply> listLogFiles(
        mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListLogFilesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Content of one log file from &#92;&#92;host.lan&#92;Data&#92;logs (see ListLogFiles). Truncated to the
     * LAST max_bytes bytes if larger (0 = server default, 256 KiB).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.GetLogFileReply> getLogFile(
        mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLogFileMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * On-demand dump of recent Windows Event Log entries (Service Control Manager events,
     * shutdown/restart events, Application errors) - the same data boot-diag.bat captures on a
     * service state change, available here without waiting for that trigger.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply> getEventLogEntries(
        mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEventLogEntriesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Screenshot of a Windows interactive session's whole desktop - a specific
     * MrpcTerminalUser&lt;N&gt; session, or (if session_user_name is empty) the pod's main/autologon
     * session, the one startup.bat runs in.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> captureSessionScreenshot(
        mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCaptureSessionScreenshotMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Same as CaptureSessionScreenshot, but fanned out from THIS pod to a DIFFERENT pod by IP
     * (mirroring the ActiveTerminalsCluster fan-out) - a browser can only reach the pod that is
     * currently serving it, not other pods' ClusterIPs, so the cluster admin view asks whichever
     * pod it's connected to relay the request.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply> captureSessionScreenshotOnPod(
        mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCaptureSessionScreenshotOnPodMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Runs, in-process (LocalSystem, no guest logon/SSH needed), the same refresh
     * startup.bat performs on a successful ONLOGON boot: robocopy published-app (including
     * mrpc-rest) from the host share, then create/start MrpcRestService. Exists because
     * ONLOGON is unreliable (autologon-dependent) - this lets a stuck deploy be repaired
     * through the Terminal Manager's own already-running gRPC/HTTP API instead of waiting
     * on that trigger or requiring interactive guest access.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply> refreshMrpcRest(
        mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRefreshMrpcRestMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Build/version identity of the Terminal Manager running on THIS pod, so a deploy can be
     * VERIFIED rather than guessed. No admin_key required - it exposes no secrets. mrpc-rest
     * transcodes GET /version-tm onto this (its own build is at GET /version).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.VersionReply> getVersion(
        mrpc_admin.Mt4TermApiAdmin.VersionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVersionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * One-shot bundle of EVERY diagnostic log available on this pod: every *.log file on the
     * shared Data folder AND in C:&#92;OEM, plus the Windows Application + System event logs. See the
     * mt5 admin proto for the canonical definition (this copy exists for the mt4 client libs).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply> getAllLogs(
        mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllLogsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Session restore logs for the latest startup sequence on a pod (stored in MongoDB session_restore_logs).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply> getSessionRestoreLogs(
        mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSessionRestoreLogsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Session restore watcher status (terminals loaded, queue count, state, diagnostics) for THIS pod.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply> getSessionRestoreStatus(
        mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSessionRestoreStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ACTIVE_TERMINALS = 0;
  private static final int METHODID_ACTIVE_TERMINALS_CLUSTER = 1;
  private static final int METHODID_SYSTEM_USAGE = 2;
  private static final int METHODID_LIST_LOG_FILES = 3;
  private static final int METHODID_GET_LOG_FILE = 4;
  private static final int METHODID_GET_EVENT_LOG_ENTRIES = 5;
  private static final int METHODID_CAPTURE_SESSION_SCREENSHOT = 6;
  private static final int METHODID_CAPTURE_SESSION_SCREENSHOT_ON_POD = 7;
  private static final int METHODID_REFRESH_MRPC_REST = 8;
  private static final int METHODID_GET_VERSION = 9;
  private static final int METHODID_GET_ALL_LOGS = 10;
  private static final int METHODID_GET_SESSION_RESTORE_LOGS = 11;
  private static final int METHODID_GET_SESSION_RESTORE_STATUS = 12;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AdminApiImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AdminApiImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ACTIVE_TERMINALS:
          serviceImpl.activeTerminals((mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsReply>) responseObserver);
          break;
        case METHODID_ACTIVE_TERMINALS_CLUSTER:
          serviceImpl.activeTerminalsCluster((mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsClusterReply>) responseObserver);
          break;
        case METHODID_SYSTEM_USAGE:
          serviceImpl.systemUsage((mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.SystemUsageReply>) responseObserver);
          break;
        case METHODID_LIST_LOG_FILES:
          serviceImpl.listLogFiles((mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.ListLogFilesReply>) responseObserver);
          break;
        case METHODID_GET_LOG_FILE:
          serviceImpl.getLogFile((mrpc_admin.Mt4TermApiAdmin.GetLogFileRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetLogFileReply>) responseObserver);
          break;
        case METHODID_GET_EVENT_LOG_ENTRIES:
          serviceImpl.getEventLogEntries((mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetEventLogEntriesReply>) responseObserver);
          break;
        case METHODID_CAPTURE_SESSION_SCREENSHOT:
          serviceImpl.captureSessionScreenshot((mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply>) responseObserver);
          break;
        case METHODID_CAPTURE_SESSION_SCREENSHOT_ON_POD:
          serviceImpl.captureSessionScreenshotOnPod((mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotOnPodRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.CaptureSessionScreenshotReply>) responseObserver);
          break;
        case METHODID_REFRESH_MRPC_REST:
          serviceImpl.refreshMrpcRest((mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.RefreshMrpcRestReply>) responseObserver);
          break;
        case METHODID_GET_VERSION:
          serviceImpl.getVersion((mrpc_admin.Mt4TermApiAdmin.VersionRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.VersionReply>) responseObserver);
          break;
        case METHODID_GET_ALL_LOGS:
          serviceImpl.getAllLogs((mrpc_admin.Mt4TermApiAdmin.GetAllLogsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetAllLogsReply>) responseObserver);
          break;
        case METHODID_GET_SESSION_RESTORE_LOGS:
          serviceImpl.getSessionRestoreLogs((mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreLogsReply>) responseObserver);
          break;
        case METHODID_GET_SESSION_RESTORE_STATUS:
          serviceImpl.getSessionRestoreStatus((mrpc_admin.Mt4TermApiAdmin.ActiveTerminalsRequest) request,
              (io.grpc.stub.StreamObserver<mrpc_admin.Mt4TermApiAdmin.GetSessionRestoreStatusReply>) responseObserver);
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

  private static abstract class AdminApiBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminApiBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mrpc_admin.Mt4TermApiAdmin.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminApi");
    }
  }

  private static final class AdminApiFileDescriptorSupplier
      extends AdminApiBaseDescriptorSupplier {
    AdminApiFileDescriptorSupplier() {}
  }

  private static final class AdminApiMethodDescriptorSupplier
      extends AdminApiBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AdminApiMethodDescriptorSupplier(String methodName) {
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
      synchronized (AdminApiGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminApiFileDescriptorSupplier())
              .addMethod(getActiveTerminalsMethod())
              .addMethod(getActiveTerminalsClusterMethod())
              .addMethod(getSystemUsageMethod())
              .addMethod(getListLogFilesMethod())
              .addMethod(getGetLogFileMethod())
              .addMethod(getGetEventLogEntriesMethod())
              .addMethod(getCaptureSessionScreenshotMethod())
              .addMethod(getCaptureSessionScreenshotOnPodMethod())
              .addMethod(getRefreshMrpcRestMethod())
              .addMethod(getGetVersionMethod())
              .addMethod(getGetAllLogsMethod())
              .addMethod(getGetSessionRestoreLogsMethod())
              .addMethod(getGetSessionRestoreStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
