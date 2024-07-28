package cn.zzwtsy.panda.config

import cn.zzwtsy.panda.service.WebSocketService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServerEndpointExporter

@Configuration
class WebSocketConfig {
    @Bean
    fun serverEndpointExporter(): ServerEndpointExporter {
        val exporter = ServerEndpointExporter()
        exporter.setAnnotatedEndpointClasses(WebSocketService::class.java)
        return exporter
    }
}