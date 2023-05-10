package com.example.noteswithrestapi.authentication_feature.presentation.verify_email

sealed class VerifyEmailEvent {

    data class OnCodeInputValueChange(val newValue: String) : VerifyEmailEvent()

    object OnConfirmClick: VerifyEmailEvent()

    object OnResendCodeClick: VerifyEmailEvent()

}