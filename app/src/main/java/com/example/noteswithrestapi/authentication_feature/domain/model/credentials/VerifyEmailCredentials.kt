package com.example.noteswithrestapi.authentication_feature.domain.model.credentials

data class VerifyEmailCredentials(
    val email: String,
    val verification_code: String
)
