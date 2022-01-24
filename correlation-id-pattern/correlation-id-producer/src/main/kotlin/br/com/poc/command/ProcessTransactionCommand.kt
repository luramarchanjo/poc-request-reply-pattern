package br.com.poc.command

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class ProcessTransactionCommand (
    val amount: Double,
    val processAt: LocalDateTime,
    val metadata: Metadata = Metadata()
) : Serializable

data class Metadata(val correlationId: UUID = UUID.randomUUID()) : Serializable