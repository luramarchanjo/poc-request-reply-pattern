package br.com.poc

import br.com.poc.event.TransactionRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class ApplicationListener(val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["request-queue"])
    fun execute(request: TransactionRequest) {
        log.info("Processing request=[$request]")
    }

}