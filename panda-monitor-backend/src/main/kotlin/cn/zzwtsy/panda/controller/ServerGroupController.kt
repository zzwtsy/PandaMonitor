package cn.zzwtsy.panda.controller

import cn.zzwtsy.panda.Result
import cn.zzwtsy.panda.model.ServerGroup
import cn.zzwtsy.panda.model.dto.ServerGroupInput
import cn.zzwtsy.panda.model.name
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * @author zzwtsy
 */
@RestController
@RequestMapping("/serverGroup")
class ServerGroupController(private val kSqlClient: KSqlClient) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 添加服务器分组
     *
     * @param input 服务器分组信息
     */
    @PostMapping
    @Transactional(rollbackFor = [Throwable::class])
    fun addServerGroup(input: ServerGroupInput): Result<ServerGroup> {
        val isExist = getServerGroup(input.name)
        if (isExist != null) {
            log.warn("分组【${input.name}】已存在，无法添加")
            return Result.error(400, "分组【${input.name}】已存在，无法添加")
        }
        val result = kSqlClient.save(input).modifiedEntity
        return Result.success(result)
    }

    /**
     * 删除服务器分组
     *
     * @param serverName 服务器分组名称
     */
    @DeleteMapping("/{serverName}")
    @Transactional(rollbackFor = [Throwable::class])
    fun deleteServerGroup(@PathVariable serverName: String): Result<String> {
        val isExist = getServerGroup(serverName)
        if (isExist == null) {
            log.warn("分组【${serverName}】不存在，无法删除")
            return Result.error(400, "分组【${serverName}】不存在，无法删除")
        }
        return Result.success()
    }

    /**
     * 修改服务器分组名称
     *
     * @param input 服务器分组信息
     */
    @PutMapping
    @Transactional(rollbackFor = [Throwable::class])
    fun updateServerGroup(input: ServerGroupInput): Result<ServerGroup> {
        val isExist = getServerGroup(input.name)
        if (isExist == null) {
            log.warn("分组【${input.name}】不存在，无法修改")
            return Result.error(400, "分组【${input.name}】不存在，无法修改")
        }
        val result = kSqlClient.update(input).modifiedEntity
        return Result.success(result)
    }

    /**
     * 获取服务器分组列表
     */
    @GetMapping("/list")
    fun listServerGroup(): Result<List<ServerGroup>> {
        val result = kSqlClient.createQuery(ServerGroup::class) {
            select(table)
        }.execute()
        return Result.success(result)
    }

    private fun getServerGroup(name: String): ServerGroup? {
        return kSqlClient.createQuery(ServerGroup::class) {
            where(table.name eq name)
            select(table)
        }.fetchOneOrNull()
    }
}