package com.example.noteswithrestapi.authentication_feature.presentation.login

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.domain.usecase.IsLoggedInUseCase
import com.example.noteswithrestapi.authentication_feature.domain.usecase.LoginUseCase
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var loginState by mutableStateOf(LoginState())
        private set


    init {
        viewModelScope.launch {
            loginState = loginState.copy(isLoading = true)
            when (val result = isLoggedInUseCase()) {
                is AppResponse.Success -> {
                    loginState = loginState.copy(isLoading = false)
                    if (result.data) {
                        _uiEvent.send(LoginUiEvent.SuccessfullyLoggedIn)
                    }
                }

                is AppResponse.Failed -> {
                    loginState = loginState.copy(isLoading = false)
                    if (result.error is AppError.PoorNetworkConnectionError) {
                        loginState = loginState.copy(
                            isLoading = false,
                            errorMessage = application.getString(R.string.error_poor_network_connection)
                        )
                    }
                }
            }
        }
    }


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSignInClick -> {
                onSignInClick(loginState.emailInput, loginState.passwordInput)
            }

            is LoginEvent.OnEmailInputValueChange -> {
                loginState = loginState.copy(emailInput = event.newValue, emailErrorMessage = null)
            }

            is LoginEvent.OnPasswordInputValueChange -> {
                loginState =
                    loginState.copy(passwordInput = event.newValue, passwordErrorMessage = null)
            }

            is LoginEvent.OnChangePasswordVisualTransformation -> {
                loginState = loginState.copy(isPasswordShown = !loginState.isPasswordShown)
            }
        }
    }


    private fun onSignInClick(email: String, password: String) {
        viewModelScope.launch {
            loginState = loginState.copy(isLoading = true)

            when (val result = loginUseCase(email, password)) {
                is AppResponse.Success -> {
                    loginState = loginState.copy(isLoading = false, errorMessage = null)
                    _uiEvent.send(LoginUiEvent.SuccessfullyLoggedIn)
                }

                is AppResponse.Failed -> {
                    loginState = loginState.copy(isLoading = false, errorMessage = null)
                    handleAppError(result.error)
                }
            }
        }
    }


    private fun handleAppError(error: AppError) {
        when (error) {

            is AppError.ServerError -> {
                loginState = loginState.copy(
                    errorMessage = application.getString(R.string.error_server)
                )
            }

            is AppError.PoorNetworkConnectionError -> {
                loginState = loginState.copy(
                    errorMessage = application.getString(R.string.error_poor_network_connection),
                )
            }

            is AppError.GeneralError -> {
                loginState = loginState.copy(
                    errorMessage = application.getString(error.messageResId),
                )
            }

            is AuthenticationAppError -> {
                handleAuthAppError(error)
            }

            else -> {
                loginState = loginState.copy(
                    errorMessage = application.getString(R.string.error_unknown),
                )
                Log.e("UNEXPECTED_ERROR", error.toString())
            }

        }
    }


    private fun handleAuthAppError(error: AuthenticationAppError) {
        when (error) {
            is AuthenticationAppError.EmailNotVerifiedError -> {
                viewModelScope.launch {
                    _uiEvent.send(LoginUiEvent.EmailNotVerified(loginState.emailInput))
                }
            }

            is AuthenticationAppError.InvalidEmailOrPasswordError -> {
                loginState = loginState.copy(
                    errorMessage = application.getString(R.string.error_invalid_email_or_password),
                )
            }

            is AuthenticationAppError.EmptyEmailError -> {
                loginState = loginState.copy(
                    emailErrorMessage = application.getString(R.string.error_empty_email)
                )
            }

            is AuthenticationAppError.EmptyPasswordError -> {
                loginState = loginState.copy(
                    passwordErrorMessage = application.getString(R.string.error_empty_password)
                )
            }

            is AuthenticationAppError.InvalidEmailError -> {
                loginState = loginState.copy(
                    emailErrorMessage = application.getString(R.string.error_invalid_email)
                )
            }

            is AuthenticationAppError.ShortPasswordError -> {
                loginState = loginState.copy(
                    passwordErrorMessage = application.getString(R.string.error_short_password)
                )
            }

            else -> {
                loginState = loginState.copy(
                    errorMessage = application.getString(R.string.error_unknown),
                )
                Log.e("UNEXPECTED_ERROR", error.toString())
            }
        }
    }

}