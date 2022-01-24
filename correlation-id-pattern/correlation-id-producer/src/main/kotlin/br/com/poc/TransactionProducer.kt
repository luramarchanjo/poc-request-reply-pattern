package br.com.poc

import br.com.poc.event.TransactionRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TransactionProducer(val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 1000L)
    fun execute() {
        val request = buildTransactionRequest()
        rabbitTemplate.convertAndSend("request-queue", request)
        log.info("Sent $request")
    }

    private fun buildTransactionRequest() = TransactionRequest(Math.random(), LocalDateTime.now())

}