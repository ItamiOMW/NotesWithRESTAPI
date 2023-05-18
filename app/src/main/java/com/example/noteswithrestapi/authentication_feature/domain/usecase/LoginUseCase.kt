package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.LoginCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, password: String): AppResponse<String> {
        if (email.isBlank()) {
            return AppResponse.failed(AuthenticationAppError.EmptyEmailError)
        }
        if (password.isBlank()) {
            return AppResponse.failed(AuthenticationAppError.EmptyPasswordError)
        }
        val loginCredentials = LoginCredentials(email, password)
        return authenticationRepository.login(loginCredentials)

    }

}