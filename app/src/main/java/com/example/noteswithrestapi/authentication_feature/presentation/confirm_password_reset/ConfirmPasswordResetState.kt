package com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset

data class ConfirmPasswordResetState(
    val passwordInput: String = "",
    val passwordError: String? = null,
    val isPasswordShown: Boolean = false,

    val confirmPasswordInput: String = "",
    val confirmPasswordError: String? = null,
    val isConfirmPasswordShown: Boolean = false,

    val codeInput: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)