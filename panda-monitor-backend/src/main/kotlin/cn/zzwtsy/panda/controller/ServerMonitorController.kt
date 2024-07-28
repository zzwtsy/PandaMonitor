package cn.zzwtsy.panda.controller

import cn.zzwtsy.panda.Result
import cn.zzwtsy.panda.model.ServerMonitor
import cn.zzwtsy.panda.model.dto.AddServerMonitorInput
import cn.zzwtsy.panda.model.dto.ListServerMonitorView
import cn.zzwtsy.panda.model.dto.UpdateServerMonitorInput
import cn.zzwtsy.panda.model.id
import cn.zzwtsy.panda.model.serverName
import cn.zzwtsy.panda.service.ServerMonitorService
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * @author zzwtsy
 */
@RestController
@RequestMapping("/serverMonitor")
class ServerMonitorController(
    private val kSqlClient: KSqlClient,
    private val serverMonitorService: ServerMonitorService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 获取服务器监控列表
     */
    @GetMapping("/list")
    fun listServerMonitor(): Result<List<ListServerMonitorView>> {
        val listServerMonitor = serverMonitorService.listServerMonitor()

        return Result.success(listServerMonitor)
    }

    /**
     * 添加服务器监控
     *
     * @param input 服务器监控信息
     */
    @PostMapping
    @Transactional(rollbackFor = [Throwable::class])
    fun addServerMonitor(input: AddServerMonitorInput): Result<ListServerMonitorView> {
        val isExist = kSqlClient.createQuery(ServerMonitor::class) {
            where(table.serverName eq input.serverName)
            select(table)
        }.fetchOneOrNull()

        if (isExist != null) {
            log.warn("服务器监控【${input.serverName}】已存在，无法添加")
            return Result.error(400, "服务器监控【${input.serverName}】已存在，无法添加")
        }

        val result = runCatching {
            kSqlClient.save(input)
        }.onFailure {
            log.error("添加服务器监控失败", it)
            throw it
        }.getOrThrow()

        if (result.isModified) {
            log.info("添加服务器监控成功，serverMonitorId=${result.modifiedEntity.id}")
            return Result.success(ListServerMonitorView(result.modifiedEntity))
        }

        log.warn("添加服务器监控失败，serverMonitorId=${result.modifiedEntity.id}")
        return Result.error(400, "添加服务器监控失败")
    }

    /**
     * 更新服务器监控信息
     *
     * @param input 更新的服务器监控信息
     */
    @PutMapping
    @Transactional(rollbackFor = [Throwable::class])
    fun updateServerMonitor(input: UpdateServerMonitorInput): Result<List<ListServerMonitorView>> {
        val serverMonitor = kSqlClient.createQuery(ServerMonitor::class) {
            where(table.id eq input.id)
            select(table)
        }.fetchOneOrNull()

        if (serverMonitor == null) {
            log.warn("服务器监控不存在，无法更新，serverMonitorId=${input.id}")
            return Result.error(400, "无法更新【${input.serverName}】监控信息，服务器监控不存在")
        }

        return listServerMonitor()
    }

    /**
     * 删除服务器监控
     *
     * @param serverMonitorId 服务器监控 id
     */
    @DeleteMapping("/{serverMonitorId}")
    @Transactional(rollbackFor = [Throwable::class])
    fun deleteServerMonitor(@PathVariable serverMonitorId: Long): Result<List<ListServerMonitorView>> {
        val viewList = serverMonitorService.deleteServerMonitor(serverMonitorId)
        return Result.success(viewList)
    }
}