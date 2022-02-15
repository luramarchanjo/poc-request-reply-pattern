package br.com.poc.event

data class TransactionResponse(
    val status: String,
    val metadata: Metadata = Metadata()
)