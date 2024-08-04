package cn.zzwtsy.panda.config

import cn.zzwtsy.panda.sql.SQLiteDialect
import com.zaxxer.hikari.HikariDataSource
import org.babyfish.jimmer.sql.dialect.Dialect
import org.babyfish.jimmer.sql.dialect.MySqlDialect
import org.babyfish.jimmer.sql.dialect.PostgresDialect
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * @author zzwtsy
 */
@Configuration
class DatabaseConfig(
    @Value("\${config.db-url}")
    private val dbUrl: String,
    @Value("\${config.db-username}")
    private val dbUsername: String,
    @Value("\${config.db-password}")
    private val dbPassword: String,
) {

    @Bean(name = ["mysqlDataSource"])
    fun mysqlDataSource() = HikariDataSource().apply {
        driverClassName = "com.mysql.cj.jdbc.Driver"
        jdbcUrl = dbUrl
        username = dbUsername
        password = dbPassword
    }

    @Bean(name = ["postgresqlDataSource"])
    fun postgresqlDataSource() = HikariDataSource().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = dbUrl
        username = dbUsername
        password = dbPassword
    }

    @Bean(name = ["sqliteDataSource"])
    fun sqliteDataSource() = HikariDataSource().apply {
        driverClassName = "org.sqlite.JDBC"
        jdbcUrl = "jdbc:sqlite:/data/panda_monitor.db"
    }

    /**
     * 在启动时根据数据库类型选择对应的数据源
     */
    @Bean
    @Primary
    fun dataSource() = when {
        dbUrl.startsWith("jdbc:mysql") -> mysqlDataSource()
        dbUrl.startsWith("jdbc:postgresql") -> postgresqlDataSource()
        dbUrl.isEmpty() -> sqliteDataSource()
        else -> throw IllegalArgumentException("Unsupported database type: $dbUrl")
    }

    /**
     * 配置数据源
     */
    @Bean
    fun jimmerDialect(): Dialect = when {
        dbUrl.startsWith("jdbc:mysql") -> MySqlDialect()
        dbUrl.startsWith("jdbc:postgresql") -> PostgresDialect()
        dbUrl.startsWith("jdbc:sqlite") or dbUrl.isEmpty() -> SQLiteDialect()
        else -> throw IllegalArgumentException("Unsupported database type: $dbUrl")
    }
}