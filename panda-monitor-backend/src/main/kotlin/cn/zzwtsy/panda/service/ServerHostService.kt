package cn.zzwtsy.panda.service

import cn.zzwtsy.panda.model.ServerHost
import cn.zzwtsy.panda.model.by
import cn.zzwtsy.panda.model.id
import cn.zzwtsy.panda.model.serverMonitorId
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author zzwtsy
 */
@Service
class ServerHostService(private val kSqlClient: KSqlClient) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 保存服务器主机信息
     */
    @Transactional(rollbackFor = [Throwable::class])
    fun saveServerHost(serverHost: ServerHost): Boolean {
        runCatching {
            kSqlClient.save(serverHost)
        }.onFailure {
            log.error("save server host to db error", it)
            return false
        }.onSuccess {
            return true
        }
        return false
    }

    /**
     * 更新服务器主机 ip
     */
    @Transactional(rollbackFor = [Throwable::class])
    fun updateServerHostIP(ipv4: String, ipv6: String, countryCode: String, serverMonitorId: Long): Boolean {
        val id = findServerHostIdByServerMonitorId(serverMonitorId)
        if (id == null) {
            log.warn("服务器主机不存在，无法更新，ipv4=${ipv4}, ipv6=${ipv6}, countryCode=${countryCode}")
            return false
        }
        val serverHostIP = new(ServerHost::class).by {
            this.id = id
            this.ipv4 = ipv4
            this.ipv6 = ipv6
            this.countryCode = countryCode
        }
        runCatching {
            kSqlClient.update(serverHostIP)
        }.onFailure {
            log.error("update server host ip to db error", it)
            return false
        }.onSuccess {
            return true
        }
        return false
    }

    fun findServerHostIdByServerMonitorId(serverMonitorId: Long): Long? {
        return kSqlClient.createQuery(ServerHost::class) {
            where(table.serverMonitorId eq serverMonitorId)
            select(table.id)
        }.fetchOneOrNull()
    }
}