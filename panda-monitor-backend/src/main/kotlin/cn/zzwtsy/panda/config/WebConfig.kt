package cn.zzwtsy.panda.config

import cn.zzwtsy.panda.service.WebSocketService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServerEndpointExporter

@Configuration
class WebConfig {
    @Bean
    fun serverEndpointExporter() = ServerEndpointExporter().apply {
        setAnnotatedEndpointClasses(WebSocketService::class.java)
    }
}