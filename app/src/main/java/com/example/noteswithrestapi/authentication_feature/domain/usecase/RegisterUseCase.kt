package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.RegisterCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
    ): Response<Unit> {
        if (email.isEmpty()) {
            return Response.failed(AppError.EmptyEmailError)
        }
        if (password.isEmpty()) {
            return Response.failed(AppError.EmptyPasswordError)
        }
        if (confirmPassword.isEmpty()) {
            return Response.failed(AppError.EmptyRepeatPasswordError)
        }
        if (password.count() < 8) {
            return Response.failed(AppError.ShortPasswordError)
        }
        if (password != confirmPassword) {
            return Response.failed(AppError.PasswordsDoNotMatchError)
        }

        val registerCredentials = RegisterCredentials(email, password, confirmPassword)
        return authenticationRepository.register(registerCredentials)
    }

}