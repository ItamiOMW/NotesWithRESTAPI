package com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset

sealed class ConfirmPasswordResetEvent {

    data class OnPasswordInputValueChange(val newValue: String): ConfirmPasswordResetEvent()

    data class OnConfirmPasswordInputValueChange(val newValue: String): ConfirmPasswordResetEvent()

    object OnChangePasswordVisualTransformation: ConfirmPasswordResetEvent()

    object OnChangeConfirmPasswordVisualTransformation: ConfirmPasswordResetEvent()

    data class OnCodeInputValueChange(val newValue: String): ConfirmPasswordResetEvent()

    object OnConfirmClick: ConfirmPasswordResetEvent()

    object OnResendCodeClick: ConfirmPasswordResetEvent()

}