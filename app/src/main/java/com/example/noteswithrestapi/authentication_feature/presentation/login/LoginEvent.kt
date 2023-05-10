package com.example.noteswithrestapi.authentication_feature.presentation.login

sealed class LoginEvent {

    object OnSignInClick: LoginEvent()

    data class OnPasswordInputValueChange(val newValue: String): LoginEvent()

    data class OnEmailInputValueChange(val newValue: String): LoginEvent()

    object OnChangePasswordVisualTransformation: LoginEvent()

}
