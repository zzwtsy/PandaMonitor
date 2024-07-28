@file:Suppress("LoggingSimilarMessage")

package cn.zzwtsy.panda.service

import cn.zzwtsy.panda.model.ServerHost
import cn.zzwtsy.panda.model.ServerState
import cn.zzwtsy.panda.model.by
import cn.zzwtsy.panda.proto.ServerMonitor
import cn.zzwtsy.panda.proto.ServerMonitorServiceGrpc.ServerMonitorServiceImplBase
import grpcstarter.server.GrpcService
import io.grpc.stub.StreamObserver
import org.babyfish.jimmer.kt.new
import org.slf4j.LoggerFactory

/**
 * @author zzwtsy
 */
@GrpcService
class ServerMonitorGrpcService(
    private val serverStateService: ServerStateService,
    private val serverHostService: ServerHostService
) : ServerMonitorServiceImplBase() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun reportServerHost(
        request: ServerMonitor.ServerHostRequest?,
        responseObserver: StreamObserver<ServerMonitor.ServerResponse>?
    ) {
        if (responseObserver == null) {
            log.warn("Grpc ResponseObserver is null")
            return
        }
        if (request == null) {
            log.warn("Grpc ServerHostRequest is null")
            grpcResponse(responseObserver, false)
            return
        }
        val id = serverHostService.findServerHostIdByServerMonitorId(request.serverId)
        val success = serverHostService.saveServerHost(request.toEntity(id))
        grpcResponse(responseObserver, success)
    }

    override fun reportServerState(
        request: ServerMonitor.ServerStateRequest?,
        responseObserver: StreamObserver<ServerMonitor.ServerResponse>?
    ) {
        if (responseObserver == null) {
            log.warn("Grpc ResponseObserver is null")
            return
        }
        if (request == null) {
            log.warn("Grpc ServerStateRequest is null")
            grpcResponse(responseObserver, false)
            return
        }
        val success = serverStateService.saveServerState(request.toEntity())
        grpcResponse(responseObserver, success)
    }

    override fun updateIP(
        request: ServerMonitor.UpdateIPRequest?,
        responseObserver: StreamObserver<ServerMonitor.ServerResponse>?
    ) {
        if (responseObserver == null) {
            log.warn("Grpc ResponseObserver is null")
            return
        }
        if (request == null) {
            log.warn("Grpc UpdateIPRequest is null")
            grpcResponse(responseObserver, false)
            return
        }
        val success = serverHostService.updateServerHostIP(
            request.ipv4, request.ipv6, request.countryCode, request.serverId
        )
        grpcResponse(responseObserver, success)
    }

    private fun grpcResponse(responseObserver: StreamObserver<ServerMonitor.ServerResponse>, success: Boolean) {
        responseObserver.onNext(ServerMonitor.ServerResponse.newBuilder().setSuccess(success).build())
        responseObserver.onCompleted()
    }

    /**
     * @param id 主键，如果为空，则新增
     * @author zzwtsy
     */
    @Suppress("DuplicatedCode")
    private fun ServerMonitor.ServerHostRequest.toEntity(id: Long?): ServerHost {
        val host = this.serverHost
        return new(ServerHost::class).by {
            if (id != null) {
                this.id = id
            }
            this.serverMonitorId = this@toEntity.serverId
            this.osName = host.osName
            this.osVersion = host.osVersion
            this.distributionId = host.distributionId
            this.kernelVersion = host.kernelVersion
            this.cpu = host.cpuList.toString()
            this.cpuCores = host.cpuCores
            this.memTotal = host.memTotal
            this.diskTotal = host.diskTotal
            this.swapTotal = host.swapTotal
            this.arch = host.arch
            this.bootTime = host.bootTime
            this.ipv4 = host.ipv4
            this.ipv6 = host.ipv6
            this.countryCode = host.countryCode
            this.uploadTime = this@toEntity.uploadTime
            this.agentVersion = this@toEntity.agentVersion
        }
    }

    @Suppress("DuplicatedCode")
    private fun ServerMonitor.ServerStateRequest.toEntity(): ServerState {
        val state = this.serverState
        return new(ServerState::class).by {
            this.serverMonitorId = this@toEntity.serverId
            this.cpuUsage = state.cpuUsage
            this.memUsed = state.memUsed
            this.swapUsed = state.swapUsed
            this.diskUsed = state.diskUsed
            this.netInTransfer = state.netInTransfer
            this.netOutTransfer = state.netOutTransfer
            this.netInSpeed = state.netInSpeed
            this.netOutSpeed = state.netOutSpeed
            this.load1 = state.load1
            this.load5 = state.load5
            this.load15 = state.load15
            this.uploadTime = this@toEntity.uploadTime
            this.agentVersion = this@toEntity.agentVersion
        }
    }
}