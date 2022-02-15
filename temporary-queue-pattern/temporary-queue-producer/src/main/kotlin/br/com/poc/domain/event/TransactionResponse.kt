package br.com.poc.domain.event

data class TransactionResponse(
    val status: String,
    val metadata: Metadata
)