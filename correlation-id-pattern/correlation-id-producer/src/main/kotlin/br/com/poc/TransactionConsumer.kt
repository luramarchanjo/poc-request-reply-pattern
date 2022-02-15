package br.com.poc

import br.com.poc.domain.Transaction.TransactionStatus
import br.com.poc.domain.event.TransactionResponse
import br.com.poc.repository.TransactionInMemoryRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TransactionConsumer(
    val transactionRepository: TransactionInMemoryRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["response-queue"])
    fun execute(response: TransactionResponse) {
        log.info("Processing response=[$response]")
        val correlationId = response.metadata.correlationId
        if (transactionRepository.exists(correlationId)) {
            val transaction = transactionRepository.findById(correlationId)
                ?: throw RuntimeException("Transactin=[$correlationId] was not found")
            transaction.status = TransactionStatus.valueOf(response.status.uppercase())

            transactionRepository.save(transaction)
        } else {
            log.warn("Discarding ${TransactionResponse::class.java.simpleName} correlationId=[$correlationId]")
        }
    }

}