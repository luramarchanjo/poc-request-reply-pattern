package br.com.poc

import br.com.poc.command.ProcessTransactionCommand
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.Serializable
import java.time.LocalDateTime

@Component
class TransactionProducer(val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 1000L)
    fun execute() {
        val command = buildProcessTransactionCommand()
        rabbitTemplate.convertAndSend("request-queue", command)
        log.info("Sent $command")
    }

    private fun buildProcessTransactionCommand() = ProcessTransactionCommand(Math.random(), LocalDateTime.now())

}