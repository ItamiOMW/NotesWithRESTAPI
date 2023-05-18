package com.example.noteswithrestapi.authentication_feature.domain.usecase

import androidx.core.text.isDigitsOnly
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.VerifyEmailCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, code: String): AppResponse<Unit> {
        if (email.isEmpty()) {
            return AppResponse.failed(AuthenticationAppError.EmptyEmailError)
        }

        if (code.length < 6) {
            return AppResponse.failed(AuthenticationAppError.InvalidVerificationCodeError)
        }

        if (!code.isDigitsOnly()) {
            return AppResponse.failed(AuthenticationAppError.InvalidVerificationCodeError)
        }

        val verifyEmailCredentials = VerifyEmailCredentials(email, code)

        return authenticationRepository.verifyEmail(verifyEmailCredentials)
    }

}