package com.example.noteswithrestapi.authentication_feature.presentation.reset_password

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.domain.usecase.SendPasswordResetCodeUseCase
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val sendPasswordResetCodeUseCase: SendPasswordResetCodeUseCase,
    private val application: Application,
) : ViewModel() {


    private val _uiEvent = Channel<ResetPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var resetPasswordState by mutableStateOf(ResetPasswordState())
        private set


    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.OnResetPasswordClick -> {
                sendResetCode(resetPasswordState.emailInput)
            }

            is ResetPasswordEvent.OnEmailInputValueChange -> {
                resetPasswordState = resetPasswordState.copy(
                    emailInput = event.newValue,
                    emailError = null
                )
            }
        }
    }


    private fun sendResetCode(email: String) {
        viewModelScope.launch {
            resetPasswordState = resetPasswordState.copy(isLoading = true)

            when (val result = sendPasswordResetCodeUseCase(email)) {
                is Response.Success -> {
                    resetPasswordState = resetPasswordState.copy(isLoading = false)
                    _uiEvent.send(ResetPasswordUiEvent.OnResetCodeSentSuccessfully(email))
                }

                is Response.Failed -> {
                    resetPasswordState = resetPasswordState.copy(isLoading = false)
                    handleAppError(result.error)
                }
            }

        }
    }


    private fun handleAppError(error: AppError) {
        viewModelScope.launch {
            when (error) {
                is AppError.EmptyEmailError -> {
                    resetPasswordState = resetPasswordState.copy(
                        emailError = application.getString(R.string.error_empty_email),
                    )
                }

                is AppError.GeneralError -> {
                    resetPasswordState = resetPasswordState.copy(
                        errorMessage = application.getString(error.messageResId),
                    )
                }

                is AppError.InvalidEmailError -> {
                    resetPasswordState = resetPasswordState.copy(
                        emailError = application.getString(R.string.error_invalid_email),
                    )
                }

                is AppError.PoorNetworkConnection -> {
                    resetPasswordState = resetPasswordState.copy(
                        errorMessage = application.getString(R.string.error_poor_network_connection),
                    )
                }


                is AppError.UserDoesNotExist -> {
                    resetPasswordState = resetPasswordState.copy(
                        errorMessage = application.getString(R.string.error_user_does_not_exists),
                    )
                }

                else -> {
                    resetPasswordState = resetPasswordState.copy(
                        errorMessage = application.getString(R.string.error_unknown),
                    )
                    Log.e("UNEXPECTED_ERROR", error.toString())
                }
            }
        }
    }

}