package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.PasswordResetConfirmCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import javax.inject.Inject

class ConfirmPasswordResetUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        code: String,
    ): AppResponse<Unit> {

        if (email.isEmpty()) {
            return AppResponse.failed(AuthenticationAppError.EmptyEmailError)
        }

        if (password.isEmpty()) {
            return AppResponse.failed(AuthenticationAppError.EmptyPasswordError)
        }

        if (password.length < 8) {
            return AppResponse.failed(AuthenticationAppError.ShortPasswordError)
        }

        if (password != confirmPassword) {
            return AppResponse.failed(AuthenticationAppError.PasswordsDoNotMatchError)
        }

        val passwordResetCodeCredentials = PasswordResetConfirmCredentials(email, password, code)

        return authenticationRepository.confirmPasswordReset(passwordResetCodeCredentials)
    }

}