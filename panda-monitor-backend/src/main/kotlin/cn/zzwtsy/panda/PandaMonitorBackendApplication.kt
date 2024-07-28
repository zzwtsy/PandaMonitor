package cn.zzwtsy.panda

import org.babyfish.jimmer.client.EnableImplicitApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableImplicitApi
class PandaMonitorBackendApplication

fun main(args: Array<String>) {
    runApplication<PandaMonitorBackendApplication>(*args)
}
