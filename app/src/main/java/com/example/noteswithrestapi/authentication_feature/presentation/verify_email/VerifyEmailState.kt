package com.example.noteswithrestapi.authentication_feature.presentation.verify_email

data class VerifyEmailState(
    val codeInput: String = "",
    val codeError: String? = null,

    val isLoading: Boolean = false,
)