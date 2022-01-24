package br.com.poc.domain.event

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class TransactionRequest (
    val amount: Double,
    val processAt: LocalDateTime,
    val metadata: Metadata = Metadata()
) : Serializable

data class Metadata(val correlationId: UUID = UUID.randomUUID()) : Serializable