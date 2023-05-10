package com.example.noteswithrestapi.authentication_feature.presentation.reset_password

sealed class ResetPasswordUiEvent {

    data class OnResetCodeSentSuccessfully(val email: String): ResetPasswordUiEvent()

}