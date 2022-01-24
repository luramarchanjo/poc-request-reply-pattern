package br.com.poc

import br.com.poc.domain.Transaction
import br.com.poc.domain.Transaction.TransactionStatus.PENDING
import br.com.poc.domain.event.TransactionRequest
import br.com.poc.repository.TransactionInMemoryRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime.now
import kotlin.random.Random

@Component
class TransactionProducer(
    val transactionRepository: TransactionInMemoryRepository,
    val rabbitTemplate: RabbitTemplate
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 5000L)
    fun execute() {
        val transaction = buildAndSaveTransaction()
        val request = buildTransactionRequest(transaction)

        rabbitTemplate.convertAndSend("request-queue", request)
        log.info("Sent $request")
    }

    private fun buildAndSaveTransaction(): Transaction {
        val transaction = Transaction(amount = Random.nextDouble(75.00, 150.00), now(), PENDING)
        return this.transactionRepository.save(transaction)!!
    }

    private fun buildTransactionRequest(transaction: Transaction) =
        TransactionRequest(transaction.amount, transaction.processAt)

}