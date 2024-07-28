package cn.zzwtsy.panda.service

import cn.zzwtsy.panda.model.ServerState
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author zzwtsy
 */
@Service
class ServerStateService(private val kSqlClient: KSqlClient) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 保存服务器状态
     */
    @Transactional(rollbackFor = [Throwable::class])
    fun saveServerState(serverState: ServerState): Boolean {
        runCatching {
            kSqlClient.save(serverState)
        }.onFailure {
            log.error("save server state to db error", it)
            return false
        }.onSuccess {
            if (it.isModified.not()) log.warn("save server state to db failed")
            return true
        }
        // ??
        return false
    }
}