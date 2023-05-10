package com.example.noteswithrestapi.authentication_feature.domain.model.credentials

data class RegisterCredentials(
    val email: String,
    val password: String,
    val confirmPassword: String,
)