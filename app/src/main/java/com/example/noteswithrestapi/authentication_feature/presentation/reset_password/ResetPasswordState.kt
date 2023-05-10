package com.example.noteswithrestapi.authentication_feature.presentation.reset_password

data class ResetPasswordState(
    val emailInput: String = "",
    val emailError: String? = null,

    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)