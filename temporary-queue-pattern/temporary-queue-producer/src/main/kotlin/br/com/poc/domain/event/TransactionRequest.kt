package br.com.poc.domain.event

import java.time.LocalDateTime
import java.util.*

data class TransactionRequest(
    val amount: Double,
    val processAt: LocalDateTime,
    val metadata: Metadata
)

data class Metadata(val correlationId: UUID, val replyAt: String)