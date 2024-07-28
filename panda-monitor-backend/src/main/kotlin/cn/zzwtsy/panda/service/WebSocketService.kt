package cn.zzwtsy.panda.service

import cn.zzwtsy.panda.Result
import cn.zzwtsy.panda.dto.ServerMonitorDTO
import cn.zzwtsy.panda.dto.WebSocketDTO
import cn.zzwtsy.panda.model.*
import cn.zzwtsy.panda.model.dto.ServerGroupView
import cn.zzwtsy.panda.model.dto.ServerHostSocketView
import cn.zzwtsy.panda.model.dto.ServerMonitorView
import cn.zzwtsy.panda.model.dto.ServerStateSocketView
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.websocket.OnClose
import jakarta.websocket.OnError
import jakarta.websocket.OnOpen
import jakarta.websocket.Session
import jakarta.websocket.server.ServerEndpoint
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.gt
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

@ServerEndpoint("/api/ws")
@Component
class WebSocketService : ApplicationContextAware {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        lateinit var APPLICATION_CONTEXT: ApplicationContext
    }

    private lateinit var objectMapper: ObjectMapper

    private lateinit var kSqlClient: KSqlClient

    private val isVisitor = AtomicBoolean(true)
    private val virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor()

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        APPLICATION_CONTEXT = applicationContext
    }

    /**
     * 初始化
     */
    private fun init() {
        this.objectMapper = APPLICATION_CONTEXT.getBean(ObjectMapper::class.java)
        this.kSqlClient = APPLICATION_CONTEXT.getBean(KSqlClient::class.java)
    }

    @OnOpen
    fun onOpen(session: Session) {
        // onOpen 只会执行一次
        init()
        log.info("WebSocket connected：id=${session.id}")
        virtualThreadExecutor.submit {
            while (true) {
                sendMessage(session)
                Thread.sleep(2000)
            }
        }
    }

    @OnClose
    fun onClose(session: Session) {
        log.info("WebSocket closed：id=${session.id}")
        webSocketClose(session)
    }

    @OnError
    fun onError(error: Throwable, session: Session) {
        log.error("WebSocket error：id=${session.id}", error)
        webSocketClose(session)
        val result = Result.error<String>(500, "未知错误：${session.id}")
        sendText(session, jsonToString(result))
    }

    private fun sendMessage(session: Session) {
        if (session.isOpen.not()) {
            return
        }
        try {
            val state = kSqlClient.createQuery(ServerState::class) {
                // 当前时间戳（秒） 4 秒钟前
//                val twoSecondsAgo = (System.currentTimeMillis() / 1000) - 4
//                where(table.uploadTime gt twoSecondsAgo)
                orderBy(table.uploadTime.desc())
                select(table.fetch(ServerStateSocketView::class))
            }.execute().distinctBy { it.serverMonitorId }

            val host = kSqlClient.createQuery(ServerHost::class) {
                select(table.fetch(ServerHostSocketView::class))
            }.execute()

            val monitor = kSqlClient.createQuery(ServerMonitor::class) {
                where(table.visitorHiding eq isVisitor.get())
                select(table.fetch(ServerMonitorView::class))
            }.execute().map { monitor ->
                ServerMonitorDTO(
                    id = monitor.id,
                    sortId = monitor.sortId,
                    serverName = monitor.serverName,
                    serverHost = host.sortedBy { it.id }.find { it.serverMonitorId == monitor.id }?.toEntity(),
                    serverState = state.sortedBy { it.id }.find { it.serverMonitorId == monitor.id }?.toEntity()
                )
            }

            val serverGroups = kSqlClient.createQuery(ServerGroup::class) {
                select(table.fetch(ServerGroupView::class))
            }.execute().map { group ->
                WebSocketDTO(
                    id = group.id,
                    groupName = group.name,
                    serverMonitor = monitor
                )
            }

            sendText(session, jsonToString(Result.success(serverGroups)))
        } catch (e: Exception) {
            log.error("WebSocket error：${session.id}", e)
            webSocketClose(session)
            val result = Result.error<String>(500, "未知错误")
            sendText(session, jsonToString(result))
        }
    }

    private fun sendText(session: Session, text: String) {
        if (session.isOpen) {
            session.basicRemote.sendText(text)
        }
    }

    private fun jsonToString(json: Any): String {
        return objectMapper.writeValueAsString(json) ?: ""
    }

    private fun webSocketClose(session: Session) {
        if (session.isOpen) {
            session.close()
        }
        if (virtualThreadExecutor.isShutdown) {
            virtualThreadExecutor.shutdownNow()
        }
    }

}