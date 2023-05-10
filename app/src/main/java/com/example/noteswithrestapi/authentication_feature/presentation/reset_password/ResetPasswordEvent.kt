package com.example.noteswithrestapi.authentication_feature.presentation.reset_password

sealed class ResetPasswordEvent {

    data class OnEmailInputValueChange(val newValue: String): ResetPasswordEvent()

    object OnResetPasswordClick: ResetPasswordEvent()

}
