package br.com.poc.event

import java.time.LocalDateTime
import java.util.*

data class TransactionRequest (
    val amount: Double,
    val processAt: LocalDateTime,
    val metadata: Metadata = Metadata()
)

data class Metadata(val correlationId: UUID = UUID.randomUUID())