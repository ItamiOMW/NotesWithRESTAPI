package com.example.noteswithrestapi.profile_feature.presentation.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.profile_feature.domain.usecase.GetUserUseCase
import com.example.noteswithrestapi.profile_feature.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val application: Application,
) : ViewModel() {


    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ProfileState())
        private set


    init {
        getUser()
    }


    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnLogout -> {
                logout()
            }

            is ProfileEvent.OnRetryLoadProfile -> {
                getUser()
            }
        }
    }


    private fun logout() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            logoutUseCase.invoke()
            state = state.copy(isLoading = false)
            _uiEvent.send(ProfileUiEvent.OnLogoutComplete)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            when (val result = getUserUseCase()) {
                is AppResponse.Success -> {
                    state = state.copy(isLoading = false, user = result.data)
                }

                is AppResponse.Failed -> {
                    state = state.copy(isLoading = false)
                    handleAppError(result.error)
                }
            }
        }
    }


    private fun handleAppError(error: AppError) {
        when (error) {
            is AppError.GeneralError -> {
                state = state.copy(errorMessage = application.getString(error.messageResId))
            }

            is AppError.ServerError -> {
                state = state.copy(errorMessage = application.getString(R.string.error_server))
            }

            is AppError.PoorNetworkConnectionError -> {
                state = state.copy(errorMessage = application.getString(R.string.error_poor_network_connection))
            }

            else -> {
                state = state.copy(errorMessage = application.getString(R.string.error_unknown))
            }
        }
    }


}