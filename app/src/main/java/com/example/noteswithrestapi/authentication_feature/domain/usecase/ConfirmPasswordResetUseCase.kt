package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.PasswordResetConfirmCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import javax.inject.Inject

class ConfirmPasswordResetUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        code: String,
    ): Response<Unit> {

        if (email.isEmpty()) {
            return Response.failed(AppError.EmptyEmailError)
        }

        if (password.isEmpty()) {
            return Response.failed(AppError.EmptyPasswordError)
        }

        if (password.length < 8) {
            return Response.failed(AppError.ShortPasswordError)
        }

        if (password != confirmPassword) {
            return Response.failed(AppError.PasswordsDoNotMatchError)
        }

        val passwordResetCodeCredentials = PasswordResetConfirmCredentials(email, password, code)

        return authenticationRepository.confirmPasswordReset(passwordResetCodeCredentials)
    }

}