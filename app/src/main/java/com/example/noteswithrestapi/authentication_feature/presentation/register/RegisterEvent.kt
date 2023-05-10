package com.example.noteswithrestapi.authentication_feature.presentation.register

sealed class RegisterEvent {

    object OnSignUpClick: RegisterEvent()

    data class OnEmailInputValueChange(val newValue: String): RegisterEvent()

    data class OnPasswordInputValueChange(val newValue: String): RegisterEvent()

    data class OnConfirmPasswordInputValueChange(val newValue: String): RegisterEvent()

    object OnChangePasswordVisualTransformation: RegisterEvent()

    object OnChangeConfirmPasswordVisualTransformation: RegisterEvent()

}
