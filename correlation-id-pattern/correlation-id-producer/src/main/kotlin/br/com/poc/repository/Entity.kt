package br.com.poc.repository

interface Entity<ID> {

    fun getId(): ID?

    fun generateId(): ID

}