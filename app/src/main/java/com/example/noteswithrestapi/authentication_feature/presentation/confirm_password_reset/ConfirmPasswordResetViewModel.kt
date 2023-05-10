package com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.domain.usecase.ConfirmPasswordResetUseCase
import com.example.noteswithrestapi.authentication_feature.domain.usecase.SendPasswordResetCodeUseCase
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPasswordResetViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val confirmPasswordResetUseCase: ConfirmPasswordResetUseCase,
    private val sendPasswordResetCodeUseCase: SendPasswordResetCodeUseCase,
) : ViewModel() {


    private val _uiEvent = Channel<ConfirmPasswordResetUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ConfirmPasswordResetState())
        private set

    private lateinit var email: String

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Screen.EMAIL_ARG)?.let { email ->
                this@ConfirmPasswordResetViewModel.email = email
            } ?: throw RuntimeException("Email arg was not passed")
        }
    }


    fun onEvent(event: ConfirmPasswordResetEvent) {
        when (event) {
            is ConfirmPasswordResetEvent.OnChangeConfirmPasswordVisualTransformation -> {
                state = state.copy(isConfirmPasswordShown = !state.isConfirmPasswordShown)
            }

            is ConfirmPasswordResetEvent.OnChangePasswordVisualTransformation -> {
                state = state.copy(isPasswordShown = !state.isPasswordShown)
            }

            is ConfirmPasswordResetEvent.OnCodeInputValueChange -> {
                state = state.copy(codeInput = event.newValue)
            }

            is ConfirmPasswordResetEvent.OnConfirmClick -> {
                confirmPasswordReset(
                    email = email,
                    code = state.codeInput,
                    newPassword = state.passwordInput,
                    confirmNewPassword = state.confirmPasswordInput
                )
            }

            is ConfirmPasswordResetEvent.OnConfirmPasswordInputValueChange -> {
                state = state.copy(confirmPasswordInput = event.newValue, confirmPasswordError = null)
            }

            is ConfirmPasswordResetEvent.OnPasswordInputValueChange -> {
                state = state.copy(passwordInput = event.newValue, passwordError = null)
            }

            ConfirmPasswordResetEvent.OnResendCodeClick -> {
                resendPasswordResetCode(email)
            }
        }
    }


    private fun resendPasswordResetCode(email: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = sendPasswordResetCodeUseCase(email)) {
                is Response.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.send(ConfirmPasswordResetUiEvent.ShowSnackbar(application.getString(R.string.text_code_resent)))
                }

                is Response.Failed -> {
                    state = state.copy(isLoading = false)
                    handleAppError(result.error)
                }
            }
        }
    }


    private fun confirmPasswordReset(
        email: String,
        code: String,
        newPassword: String,
        confirmNewPassword: String,
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = confirmPasswordResetUseCase(
                email,
                newPassword,
                confirmNewPassword,
                code
            )) {
                is Response.Success -> {
                    state = state.copy(isLoading = false, errorMessage = null)
                    _uiEvent.send(ConfirmPasswordResetUiEvent.OnConfirmPasswordResetSuccess)
                }

                is Response.Failed -> {
                    state = state.copy(isLoading = false, errorMessage = null)
                    handleAppError(result.error)
                }
            }
        }
    }


    private fun handleAppError(error: AppError) {
        viewModelScope.launch {
            when (error) {
                is AppError.EmptyPasswordError -> {
                    state = state.copy(
                        passwordError = application.getString(R.string.error_empty_password)
                    )
                }

                is AppError.EmptyRepeatPasswordError -> {
                    state = state.copy(
                        confirmPasswordError = application.getString(R.string.error_empty_password)
                    )
                }

                is AppError.GeneralError -> {
                    state = state.copy(
                        errorMessage = application.getString(error.messageResId)
                    )
                }

                is AppError.InvalidVerificationCodeError -> {
                    state = state.copy(
                        errorMessage = application.getString(R.string.error_invalid_verification_code)
                    )
                }

                is AppError.PasswordsDoNotMatchError -> {
                    state = state.copy(
                        passwordError = application.getString(R.string.error_passwords_do_not_march),
                        confirmPasswordError = application.getString(R.string.error_passwords_do_not_march)
                    )
                }

                is AppError.PoorNetworkConnection -> {
                    state = state.copy(
                        errorMessage = application.getString(R.string.error_poor_network_connection)
                    )
                }

                is AppError.ShortPasswordError -> {
                    state = state.copy(
                        passwordError = application.getString(R.string.error_short_password)
                    )
                }

                else -> {
                    state = state.copy(
                        errorMessage = application.getString(R.string.error_unknown)
                    )
                    Log.e("UNKNOWN_ERROR", error.toString())
                }
            }
        }
    }


}