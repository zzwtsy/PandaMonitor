package cn.zzwtsy.panda.sql


import org.babyfish.jimmer.sql.dialect.Dialect
import org.babyfish.jimmer.sql.dialect.PaginationContext
import org.babyfish.jimmer.sql.runtime.ExecutionException
import java.math.BigDecimal
import java.sql.ResultSet
import java.time.*
import java.util.*

/**
 * SQLite dialect implementation for the Jimmer ORM framework.
 */
class SQLiteDialect : Dialect {

    /**
     * Adds pagination support using SQLite LIMIT and OFFSET clauses.
     */
    override fun paginate(ctx: PaginationContext) {
        ctx.origin().space().sql("limit ").variable(ctx.limit)
        if (ctx.offset > 0) {
            ctx.sql(" offset ").variable(ctx.offset)
        }
    }

    /**
     * Throws an exception because array types are not supported in SQLite.
     */
    override fun <T> getArray(rs: ResultSet, col: Int, arrayType: Class<Array<T>>): Array<T> {
        throw UnsupportedOperationException("`Dialect.getArray` SQLite is not supported")
    }

    override fun getSelectIdFromSequenceSql(sequenceName: String?): String? {
        throw ExecutionException("SQLite does not support sequences")
    }

    override fun isTupleSupported(): Boolean {
        // SQLite does not support tuple types
        return false
    }

    /**
     * Maps Java types to their corresponding SQLite data types.
     */
    override fun sqlType(elementType: Class<*>): String? {
        return when (elementType) {
            String::class.java -> "text"
            UUID::class.java -> "text"
            Boolean::class.java -> "integer"
            Byte::class.java -> "integer"
            Short::class.java -> "integer"
            Int::class.java -> "integer"
            Long::class.java -> "integer"
            Float::class.java -> "real"
            Double::class.java -> "real"
            BigDecimal::class.java -> "numeric"
            java.sql.Date::class.java, LocalDate::class.java -> "text"
            java.sql.Time::class.java, LocalTime::class.java -> "text"
            OffsetTime::class.java -> "text"
            java.util.Date::class.java, java.sql.Timestamp::class.java -> "text"
            LocalDateTime::class.java -> "text"
            OffsetDateTime::class.java, ZonedDateTime::class.java -> "text"
            else -> null
        }
    }

    override fun transCacheOperatorTableDDL(): String {
        return """
            CREATE TABLE JIMMER_TRANS_CACHE_OPERATOR
            (
                ID             INTEGER PRIMARY KEY AUTOINCREMENT,
                IMMUTABLE_TYPE TEXT,
                IMMUTABLE_PROP TEXT,
                CACHE_KEY      TEXT NOT NULL,
                REASON         TEXT
            )
        """.trimIndent()
    }

    /**
     * Returns the name of this class as a string representation.
     */
    override fun toString(): String {
        return javaClass.name
    }
}