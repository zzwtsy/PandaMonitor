package cn.zzwtsy.panda.service

import cn.zzwtsy.panda.model.ServerMonitor
import cn.zzwtsy.panda.model.dto.ListServerMonitorView
import cn.zzwtsy.panda.model.id
import cn.zzwtsy.panda.model.sortId
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServerMonitorService(private val kSqlClient: KSqlClient) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 获取服务器监控列表
     *
     * @return [List]<[ListServerMonitorView]> 服务器监控列表
     */
    fun listServerMonitor(): List<ListServerMonitorView> {
        val serverMonitorViews = kSqlClient.createQuery(ServerMonitor::class) {
            orderBy(table.sortId)
            select(table.fetch(ListServerMonitorView::class))
        }.execute()

        return serverMonitorViews
    }

    /**
     * 删除服务器监控
     *
     * @param serverMonitorId 服务器监控 id
     */
    @Transactional(rollbackFor = [Throwable::class])
    fun deleteServerMonitor(serverMonitorId: Long): List<ListServerMonitorView> {
        val serverMonitor = kSqlClient.createQuery(ServerMonitor::class) {
            where(table.id eq serverMonitorId)
            select(table)
        }.fetchOneOrNull()

        if (serverMonitor == null) {
            log.warn("服务器监控不存在，无法删除，serverMonitorId=${serverMonitorId}")
            throw RuntimeException("服务器监控不存在，无法删除")
        }

        val deleteCount = kSqlClient.deleteById(ServerMonitor::class, serverMonitorId).totalAffectedRowCount

        if (deleteCount == 0) {
            log.warn("服务器监控删除失败，serverMonitorId=${serverMonitorId}")
            throw RuntimeException("服务器监控删除失败")
        }

        return listServerMonitor()
    }
}