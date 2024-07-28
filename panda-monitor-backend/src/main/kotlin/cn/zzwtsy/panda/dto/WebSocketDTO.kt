package cn.zzwtsy.panda.dto

import cn.zzwtsy.panda.model.ServerHost
import cn.zzwtsy.panda.model.ServerState

data class WebSocketDTO(
    val id: Long,
    val groupName: String,
    val serverMonitor: List<ServerMonitorDTO>
)

data class ServerMonitorDTO(
    val id: Long,
    val sortId: Int,
    val serverName: String,
    val serverHost: ServerHost?,
    val serverState: ServerState?
)