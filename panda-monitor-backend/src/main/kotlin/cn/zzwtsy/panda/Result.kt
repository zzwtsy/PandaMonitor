package cn.zzwtsy.panda

import com.fasterxml.jackson.annotation.JsonInclude

data class Result<T>(
    val code: Int,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null,
    val msg: String
) {
    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(200, data, "success")
        }

        fun <T> success(): Result<T> {
            return Result(200, null, "success")
        }

        fun <T> error(msg: String): Result<T> {
            return Result(500, null, msg)
        }

        /**
         * 自定义错误码和错误信息
         */
        fun <T> error(code: Int, msg: String): Result<T> {
            return Result(code, null, msg)
        }
    }
}
