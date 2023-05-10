package com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset

sealed class ConfirmPasswordResetUiEvent {

    object OnConfirmPasswordResetSuccess : ConfirmPasswordResetUiEvent()

    data class ShowSnackbar(val message: String) : ConfirmPasswordResetUiEvent()

}