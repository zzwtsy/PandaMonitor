package cn.zzwtsy.panda.controller

import cn.zzwtsy.panda.Result
import cn.zzwtsy.panda.model.User
import cn.zzwtsy.panda.model.dto.AddUserInput
import cn.zzwtsy.panda.model.dto.UpdateUserInput
import cn.zzwtsy.panda.model.dto.UserView
import cn.zzwtsy.panda.model.id
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
@RequestMapping("/user")
class UserController(private val kSqlClient: KSqlClient) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 获取用户信息
     * @param userId 用户 id
     */
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): Result<UserView> {
        val userView = kSqlClient.createQuery(User::class) {
            where(table.id eq userId)
            select(table.fetch(UserView::class))
        }.fetchOneOrNull()
        if (userView == null) {
            log.warn("用户不存在，userId=$userId")
            return Result.error(400, "用户不存在")
        }
        return Result.success(userView)
    }

    /**
     * 修改用户信息
     * @param input 用户信息
     */
    @PutMapping
    @Transactional(rollbackFor = [Throwable::class])
    fun updateUser(input: UpdateUserInput): Result<UserView> {
        val userView = kSqlClient.createQuery(User::class) {
            where(table.id eq input.id)
            select(table.fetch(UserView::class))
        }.fetchOneOrNull()
        if (userView == null) {
            log.warn("用户不存在，userId=${input.id}，无法更新用户信息")
            return Result.error(400, "用户不存在，无法更新用户信息")
        }
        val result = kSqlClient.update(input).modifiedEntity.run { UserView(this) }
        return Result.success(result)
    }

    /**
     * 添加用户
     *
     * @param input 用户信息
     */
    @PostMapping
    @Transactional(rollbackFor = [Throwable::class])
    fun addUser(input: AddUserInput): Result<UserView> {
        if (input.name.isBlank() || input.password.isBlank()) {
            log.warn("用户名或密码为空：userName=${input.name}, password=${input.password}")
            return Result.error(400, "用户名或密码错误")
        }

        kSqlClient.createQuery(User::class) {
            where(table.name eq input.name)
            select(table.fetch(UserView::class))
        }.fetchOneOrNull()?.let {
            log.warn("用户名已存在：userName=${input.name}")
            return Result.error(400, "用户名已存在")
        }

        val result = kSqlClient.save(input)
        if (result.isModified.not()) {
            log.warn("添加用户失败：originalEntity=${result.originalEntity}\nmodifiedEntity=${result.modifiedEntity}")
            return Result.error(400, "添加用户失败")
        }
        return Result.success(UserView(result.modifiedEntity))
    }

    /**
     * 删除用户
     * @param userId 用户 id
     */
    @DeleteMapping("/{userId}")
    @Transactional(rollbackFor = [Throwable::class])
    fun deleteUser(@PathVariable userId: Long): Result<String> {
        val result = kSqlClient.deleteById(User::class, userId)
        if (result.totalAffectedRowCount == 0) {
            log.warn("删除用户失败：userId=$userId")
            return Result.error(400, "删除用户失败")
        }
        return Result.success()
    }
}