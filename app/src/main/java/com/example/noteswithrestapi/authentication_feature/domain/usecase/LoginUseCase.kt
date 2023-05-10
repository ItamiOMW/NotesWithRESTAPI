package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.LoginCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, password: String): Response<String> {
        if (email.isBlank()) {
            return Response.failed(AppError.EmptyEmailError)
        }
        if (password.isBlank()) {
            return Response.failed(AppError.EmptyPasswordError)
        }
        val loginCredentials = LoginCredentials(email, password)
        return authenticationRepository.login(loginCredentials)

    }

}