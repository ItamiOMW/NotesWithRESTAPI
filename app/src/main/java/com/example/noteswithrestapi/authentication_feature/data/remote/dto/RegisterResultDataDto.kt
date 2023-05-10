package com.example.noteswithrestapi.authentication_feature.data.remote.dto

data class RegisterResultDataDto(
    val email: String?,
    val is_active: Boolean?,
    val password: String?
)