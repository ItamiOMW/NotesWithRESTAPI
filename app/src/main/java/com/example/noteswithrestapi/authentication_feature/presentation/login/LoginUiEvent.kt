package com.example.noteswithrestapi.authentication_feature.presentation.login

sealed class LoginUiEvent {

    object SuccessfullyLoggedIn: LoginUiEvent()

    data class EmailNotVerified(val email: String): LoginUiEvent()

}
