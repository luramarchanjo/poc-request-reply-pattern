package br.com.poc.repository

import br.com.poc.domain.Transaction
import org.springframework.stereotype.Component
import java.util.*

@Component
class TransactionInMemoryRepository : InMemoryRepository<Transaction, UUID>() {

}