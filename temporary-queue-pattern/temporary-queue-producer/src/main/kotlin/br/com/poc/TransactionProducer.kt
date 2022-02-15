package br.com.poc

import br.com.poc.domain.Transaction
import br.com.poc.domain.Transaction.TransactionStatus.PENDING
import br.com.poc.domain.event.Metadata
import br.com.poc.domain.event.TransactionRequest
import br.com.poc.repository.TransactionInMemoryRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime.now
import java.util.*
import kotlin.random.Random

@Component
class TransactionProducer(
    val transactionRepository: TransactionInMemoryRepository,
    val rabbitTemplate: RabbitTemplate,
    val amqpAdmin: AmqpAdmin
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 5000L)
    fun execute() {
        val temporaryQueueName = createTemporaryQueue()
        val transaction = buildAndSaveTransaction()
        val request = buildTransactionRequest(transaction, temporaryQueueName)

        rabbitTemplate.convertAndSend("request-queue", request)
        log.info("Sent $request")
    }

    private fun createTemporaryQueue(): String {
        val temporaryQueueName = "temporary-response-queue-${UUID.randomUUID()}"
        val temporaryQueue = Queue(temporaryQueueName, false, false, true, mapOf("x-expires" to 60000L))

        return amqpAdmin.declareQueue(temporaryQueue)!!
    }

    private fun buildAndSaveTransaction(): Transaction {
        val transaction = Transaction(amount = Random.nextDouble(75.00, 150.00), now(), PENDING)
        return this.transactionRepository.save(transaction)!!
    }

    private fun buildTransactionRequest(transaction: Transaction, temporaryQueueName: String) =
        TransactionRequest(
            transaction.amount,
            transaction.processAt,
            Metadata(transaction.getId()!!, temporaryQueueName)
        )

}