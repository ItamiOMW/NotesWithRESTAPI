package com.example.noteswithrestapi.core.domain.paginator

interface Paginator {

    suspend fun loadNextPage()

    fun reset()

}