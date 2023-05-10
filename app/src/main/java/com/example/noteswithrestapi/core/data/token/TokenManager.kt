package com.example.noteswithrestapi.core.data.token

interface TokenManager {

    val authToken: String?

    fun saveToken(token: String)

    fun removeToken()

}