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
import jakarta.websocket.*
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

    private lateinit var applicationContext: ApplicationContext

    private lateinit var objectMapper: ObjectMapper

    private lateinit var kSqlClient: KSqlClient

    private val isVisitor = AtomicBoolean(true)
    private val virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor()

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    /**
     * 初始化
     *
     * [springdoc.cn](https://springdoc.cn/spring-boot-websocket)
     */
    private fun init() {
        this.objectMapper = applicationContext.getBean(ObjectMapper::class.java)
        this.kSqlClient = applicationContext.getBean(KSqlClient::class.java)
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

    @OnMessage
    fun onMessage(message: String, session: Session) {
        if (message.isBlank() && message.startsWith("Authorization:").not()) {
            isVisitor.set(false)
        } else {
            isVisitor.set(true)
            TODO("待实现 token 验证")
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
                // 当前时间戳 3 秒钟前
                val threeSecondsAgo = System.currentTimeMillis().div(1000).minus(3)
                where(table.uploadTime gt threeSecondsAgo)
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
        if (virtualThreadExecutor.isShutdown.not()) {
            virtualThreadExecutor.shutdownNow()
        }
        if (session.isOpen) {
            session.close()
        }
    }
}