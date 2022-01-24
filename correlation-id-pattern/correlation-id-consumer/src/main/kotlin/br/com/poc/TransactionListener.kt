package br.com.poc

import br.com.poc.event.TransactionRequest
import br.com.poc.event.TransactionResponse
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class TransactionListener(val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["request-queue"])
    fun execute(request: TransactionRequest) {
        log.info("Processing request=[$request]")
        val status = if (request.amount <= 100.00) "approved" else "rejected"
        val response = TransactionResponse(status, request.metadata)

        this.rabbitTemplate.convertAndSend("response-queue", response)
        log.info("Sent $response")
    }

}