package com.example.noteswithrestapi.authentication_feature.presentation.verify_email

sealed class VerifyEmailUiEvent {

    object OnEmailVerifiedSuccessfully: VerifyEmailUiEvent()

    data class ShowSnackbar(val message: String): VerifyEmailUiEvent()

}