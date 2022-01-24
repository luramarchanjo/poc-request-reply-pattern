package br.com.poc.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class InMemoryRepository<E : Entity<ID>, ID> {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val database = mutableMapOf<ID, E>()

    fun save(entity: E): E? {
        return if (entity.getId() == null) {
            val generatedId = entity.generateId()
            database.putIfAbsent(generatedId, entity)
            log.info("Saved $entity")

            entity
        } else {
            database[entity.getId()!!] = entity
            log.info("Updated $entity")

            entity
        }
    }

    fun exists(id: ID) = database.containsKey(id)

    fun findById(id: ID) = database[id]

}