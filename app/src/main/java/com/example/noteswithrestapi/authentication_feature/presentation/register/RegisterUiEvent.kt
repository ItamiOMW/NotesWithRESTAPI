package com.example.noteswithrestapi.authentication_feature.presentation.register

sealed class RegisterUiEvent {

    data class OnRegisterSuccessful(val email: String): RegisterUiEvent()

}
