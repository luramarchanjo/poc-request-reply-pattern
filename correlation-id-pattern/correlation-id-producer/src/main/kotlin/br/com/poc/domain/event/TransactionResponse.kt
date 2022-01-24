package br.com.poc.domain.event

import java.io.Serializable

data class TransactionResponse(
    val status: String,
    val metadata: Metadata = Metadata()
) : Serializable