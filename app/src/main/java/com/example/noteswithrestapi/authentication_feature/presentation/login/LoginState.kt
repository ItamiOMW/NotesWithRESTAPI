package com.example.noteswithrestapi.authentication_feature.presentation.login

data class LoginState(

    val emailInput: String = "",
    val emailErrorMessage: String? = null,

    val passwordInput: String = "",
    val isPasswordShown: Boolean = false,
    val passwordErrorMessage: String? = null,

    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
