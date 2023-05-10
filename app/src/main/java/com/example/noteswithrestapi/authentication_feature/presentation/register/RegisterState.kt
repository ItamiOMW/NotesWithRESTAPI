package com.example.noteswithrestapi.authentication_feature.presentation.register

data class RegisterState(
    val emailInput: String = "",
    val emailErrorMessage: String? = null,

    val passwordInput: String = "",
    val isPasswordShown: Boolean = false,
    val passwordErrorMessage: String? = null,

    val confirmPasswordInput: String = "",
    val isConfirmPasswordShown: Boolean = false,
    val confirmPasswordErrorMessage: String? = null,

    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
