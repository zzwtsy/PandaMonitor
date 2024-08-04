package cn.zzwtsy.panda

import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.io.File

@Component
class Init(
    @Value("\${config.db-url}")
    private val dbUrl: String,
    @Value("\${config.db-username}")
    private val dbUsername: String,
    @Value("\${config.db-password}")
    private val dbPassword: String,
) : ApplicationRunner {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 初始化数据库
     */
    override fun run(args: ApplicationArguments?) {
        when {
            dbUrl.isEmpty() -> initSQLiteDatabase()
            dbUrl.startsWith("jdbc:postgresql") -> initPostgreSQLDatabase()
            dbUrl.startsWith("jdbc:mysql") -> initMySQLDatabase()
            else -> throw IllegalArgumentException("Unsupported database type: $dbUrl")
        }
    }

    /**
     * 初始化 SQLite 数据库
     */
    fun initSQLiteDatabase() {
        val isFile = File("/data/panda_monitor.db").isFile
        if (isFile) {
            log.info("SQLite 数据库 panda_monitor 已存在，无需初始化")
            return
        }
        val dbUrl = "jdbc:sqlite:/data/panda_monitor.db"
        val sqliteDataSource = HikariDataSource().apply {
            driverClassName = "org.sqlite.JDBC"
            jdbcUrl = dbUrl
        }
        try {
            // 获取 resources 目录下的 sql 文件
            val sql = javaClass.classLoader.getResource("db/sqlite-schema.sql")?.readText()?.split(";") ?: run {
                throw RuntimeException("初始化 SQLite 数据库失败，无法读取 sql 文件")
            }
            sqliteDataSource.connection.use {
                sql.filter { sql -> sql.trim().isNotEmpty() }.forEach { sql ->
                    it.prepareStatement(sql).use { stmt -> stmt.execute() }
                }
            }
        } catch (e: Exception) {
            log.error("初始化 SQLite 数据库失败", e)
        } finally {
            sqliteDataSource.close()
        }
    }

    /**
     * 初始化 PostgreSQL 数据库
     */
    fun initPostgreSQLDatabase() {
        val postgresqlDataSource = HikariDataSource().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl
            username = dbUsername
            password = dbPassword
        }
        try {
            // 判断 panda_monitor 数据库是否存在
            val tables = postgresqlDataSource.connection.metaData.catalogs
            while (tables.next()) {
                if (tables.getString(1).equals("panda_monitor", true)) {
                    log.info("PostgreSQL 数据库 panda_monitor 已存在，无需初始化")
                    return
                }
//                log.info("PostgreSQL 数据库 panda_monitor 已存在，无需初始化")
//                return
            }
            // 获取 resources 目录下的 sql 文件
            val sql = javaClass.classLoader.getResource("db/postgresql-schema.sql")?.readText()?.split(";") ?: run {
                throw RuntimeException("初始化 PostgreSQL 数据库失败，无法读取 sql 文件")
            }
            postgresqlDataSource.connection.use {
                it.prepareStatement("""CREATE DATABASE panda_monitor;""").use { stmt ->
                    stmt.execute()
                }
                it.catalog = "panda_monitor"
                sql.filter { sql -> sql.trim().isNotEmpty() }
//                    .map { sql -> sql.replace(Regex("COMMENT ON .*;"), "public") }
                    .forEach { sql ->
                        it.prepareStatement(sql).use { stmt -> stmt.execute() }
                    }
            }
        } catch (e: Exception) {
            log.error("初始化 PostgreSQL 数据库失败", e)
        } finally {
            postgresqlDataSource.close()
        }
    }

    /**
     * 初始化 MySQL 数据库
     */
    fun initMySQLDatabase() {
        val mysqlDataSource = HikariDataSource().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = dbUrl
            username = dbUsername
            password = dbPassword
        }
        try {
            // 判断 panda_monitor 数据库是否存在
            val tables = mysqlDataSource.connection.metaData.getTables("panda_monitor", null, null, null)
            if (tables.next()) {
                log.info("MySQL 数据库 panda_monitor 已存在，无需初始化")
                return
            }
            // 获取 resources 目录下的 sql 文件
            val sql = javaClass.classLoader.getResource("db/mysql-schema.sql")?.readText()?.split(";") ?: run {
                throw RuntimeException("初始化 MySQL 数据库失败，无法读取 sql 文件")
            }
            mysqlDataSource.connection.use {
                it.prepareStatement("""CREATE DATABASE IF NOT EXISTS `panda_monitor`;""").use { stmt ->
                    stmt.execute()
                }
                it.catalog = "panda_monitor"
                sql.filter { sql -> sql.trim().isNotEmpty() }.forEach { sql ->
                    it.prepareStatement(sql).use { stmt -> stmt.execute() }
                }
            }
        } catch (e: Exception) {
            log.error("初始化 MySQL 数据库失败", e)
        } finally {
            mysqlDataSource.close()
        }
    }
}