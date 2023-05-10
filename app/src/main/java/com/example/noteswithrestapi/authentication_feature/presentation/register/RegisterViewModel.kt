package com.example.noteswithrestapi.authentication_feature.presentation.register

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.domain.usecase.RegisterUseCase
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var registerState by mutableStateOf(RegisterState())
        private set


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnEmailInputValueChange -> {
                registerState = registerState.copy(
                    emailInput = event.newValue,
                    emailErrorMessage = null
                )
            }

            is RegisterEvent.OnPasswordInputValueChange -> {
                registerState = registerState.copy(
                    passwordInput = event.newValue,
                    passwordErrorMessage = null
                )
            }

            is RegisterEvent.OnConfirmPasswordInputValueChange -> {
                registerState = registerState.copy(
                    confirmPasswordInput = event.newValue,
                    confirmPasswordErrorMessage = null
                )
            }

            is RegisterEvent.OnSignUpClick -> {
                onSignUpClick(
                    registerState.emailInput,
                    registerState.passwordInput,
                    registerState.confirmPasswordInput
                )
            }

            RegisterEvent.OnChangePasswordVisualTransformation -> {
                registerState = registerState.copy(
                    isPasswordShown = !registerState.isPasswordShown
                )
            }

            RegisterEvent.OnChangeConfirmPasswordVisualTransformation -> {
                registerState = registerState.copy(
                    isConfirmPasswordShown = !registerState.isConfirmPasswordShown
                )
            }
        }
    }


    private fun onSignUpClick(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            registerState = registerState.copy(isLoading = true)

            when (val result = registerUseCase(email, password, confirmPassword)) {
                is Response.Success -> {
                    registerState = registerState.copy(isLoading = false)
                    _uiEvent.send(RegisterUiEvent.OnRegisterSuccessful(email))
                }

                is Response.Failed -> {
                    registerState = registerState.copy(isLoading = false, errorMessage = null)
                    handleAppError(result.error)
                }
            }

        }
    }


    private fun handleAppError(error: AppError) {
        viewModelScope.launch {
            when (error) {
                is AppError.EmptyEmailError -> {
                    registerState = registerState.copy(
                        emailErrorMessage = application.getString(R.string.error_empty_email)
                    )
                }

                is AppError.EmptyPasswordError -> {
                    registerState = registerState.copy(
                        passwordErrorMessage = application.getString(R.string.error_empty_password)
                    )
                }

                is AppError.EmptyRepeatPasswordError -> {
                    registerState = registerState.copy(
                        confirmPasswordErrorMessage = application.getString(R.string.error_empty_password)
                    )
                }

                is AppError.GeneralError -> {
                    registerState = registerState.copy(
                        errorMessage = application.getString(error.messageResId)
                    )
                }

                is AppError.InvalidEmailError -> {
                    registerState = registerState.copy(
                        emailErrorMessage = application.getString(R.string.error_invalid_email)
                    )
                }

                is AppError.PasswordsDoNotMatchError -> {
                    registerState = registerState.copy(
                        errorMessage = application.getString(R.string.error_passwords_do_not_march),
                    )
                }

                is AppError.PoorNetworkConnection -> {
                    registerState = registerState.copy(
                        errorMessage = application.getString(R.string.error_poor_network_connection)
                    )
                }

                is AppError.ShortPasswordError -> {
                    registerState = registerState.copy(
                        passwordErrorMessage = application.getString(R.string.error_short_password)
                    )
                }

                is AppError.UserAlreadyExist -> {
                    registerState = registerState.copy(
                        errorMessage = application.getString(R.string.error_user_exists)
                    )
                }

                else -> {
                    registerState = registerState.copy(
                        errorMessage = application.getString(R.string.error_unknown),
                    )
                    Log.e("UNEXPECTED_ERROR", error.toString())
                }
            }
        }
    }

}