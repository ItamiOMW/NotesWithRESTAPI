package com.example.noteswithrestapi.authentication_feature.presentation.verify_email

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.domain.usecase.ResendEmailVerificationCodeUseCase
import com.example.noteswithrestapi.authentication_feature.domain.usecase.VerifyEmailUseCase
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val resendEmailVerificationCodeUseCase: ResendEmailVerificationCodeUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {


    private val _uiEvent = Channel<VerifyEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var verifyEmailState by mutableStateOf(VerifyEmailState())
        private set

    private lateinit var email: String

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Screen.EMAIL_ARG)?.let { email ->
                this@VerifyEmailViewModel.email = email
            } ?: throw RuntimeException("Email argument wasn't passed")
        }
    }


    fun onEvent(event: VerifyEmailEvent) {
        when (event) {
            is VerifyEmailEvent.OnCodeInputValueChange -> {
                verifyEmailState = verifyEmailState.copy(
                    codeInput = event.newValue,
                    codeError = null
                )
            }

            VerifyEmailEvent.OnConfirmClick -> {
                verifyEmail(email, verifyEmailState.codeInput)
            }

            VerifyEmailEvent.OnResendCodeClick -> {
                resendEmail(email)
            }
        }
    }

    private fun verifyEmail(email: String, code: String) {
        viewModelScope.launch {
            verifyEmailState = verifyEmailState.copy(isLoading = true)

            when (val result = verifyEmailUseCase(email, code)) {
                is AppResponse.Success -> {
                    verifyEmailState = verifyEmailState.copy(isLoading = false)
                    _uiEvent.send(VerifyEmailUiEvent.OnEmailVerifiedSuccessfully)
                }

                is AppResponse.Failed -> {
                    verifyEmailState = verifyEmailState.copy(isLoading = false)
                    handleAppError(result.error)
                }
            }

        }
    }

    private fun resendEmail(email: String) {
        verifyEmailState = verifyEmailState.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = resendEmailVerificationCodeUseCase(email)) {
                is AppResponse.Success -> {
                    verifyEmailState = verifyEmailState.copy(isLoading = false, codeError = null)
                    _uiEvent.send(VerifyEmailUiEvent.ShowSnackbar(application.getString(R.string.text_code_resent)))
                }

                is AppResponse.Failed -> {
                    verifyEmailState = verifyEmailState.copy(isLoading = false, codeError = null)
                    handleAppError(result.error)
                }
            }
        }
    }


    private fun handleAppError(error: AppError) {
        when (error) {
            is AppError.GeneralError -> {
                verifyEmailState = verifyEmailState.copy(
                    codeError = application.getString(error.messageResId)
                )
            }

            is AppError.PoorNetworkConnectionError -> {
                verifyEmailState = verifyEmailState.copy(
                    codeError = application.getString(R.string.error_poor_network_connection)
                )
            }

            is AppError.ServerError -> {
                verifyEmailState = verifyEmailState.copy(
                    codeError = application.getString(R.string.error_server)
                )
            }

            is AuthenticationAppError -> {
                handleAuthAppError(error)
            }

            else -> {
                verifyEmailState = verifyEmailState.copy(
                    codeError = application.getString(R.string.error_unknown)
                )
                Log.e("UNKNOWN_ERROR", error.toString())
            }
        }
    }

    private fun handleAuthAppError(error: AuthenticationAppError) {
        when (error) {
            is AuthenticationAppError.InvalidVerificationCodeError -> {
                verifyEmailState = verifyEmailState.copy(
                    codeError = application.getString(R.string.error_invalid_verification_code)
                )
            }

            else -> {
                verifyEmailState = verifyEmailState.copy(
                    codeError = application.getString(R.string.error_unknown)
                )
                Log.e("UNKNOWN_ERROR", error.toString())
            }
        }
    }

}