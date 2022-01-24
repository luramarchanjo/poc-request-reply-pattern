package br.com.poc.event

import java.io.Serializable

data class TransactionResponse(
    val status: String,
    val metadata: Metadata = Metadata()
) : Serializable