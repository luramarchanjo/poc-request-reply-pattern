package br.com.poc.domain

import br.com.poc.repository.Entity
import java.time.LocalDateTime
import java.util.*

data class Transaction(
    val amount: Double,
    val processAt: LocalDateTime,
    var status: TransactionStatus
) : Entity<UUID> {

    enum class TransactionStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    private var id: UUID? = null

    override fun generateId(): UUID {
        if (id == null) {
            id = UUID.randomUUID()
        }

        return id!!
    }

    override fun getId(): UUID? {
        return this.id
    }

}