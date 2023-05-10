package com.example.noteswithrestapi.authentication_feature.domain.model.credentials

data class PasswordResetConfirmCredentials(
    val email: String,
    val new_password: String,
    val password_reset_code: String,
)
