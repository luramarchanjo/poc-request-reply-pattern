package br.com.poc.configuration

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfiguration {

    @Bean
    fun createRequestQueue() = Queue("request-queue")

    @Bean
    fun createResponseQueue() = Queue("response-queue")

}