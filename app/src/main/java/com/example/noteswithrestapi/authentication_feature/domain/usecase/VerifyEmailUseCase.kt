package com.example.noteswithrestapi.authentication_feature.domain.usecase

import androidx.core.text.isDigitsOnly
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.VerifyEmailCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, code: String): Response<Unit> {
        if (email.isEmpty()) {
            return Response.failed(AppError.EmptyEmailError)
        }

        if (code.length < 6) {
            return Response.failed(AppError.InvalidVerificationCodeError)
        }

        if (!code.isDigitsOnly()) {
            return Response.failed(AppError.InvalidVerificationCodeError)
        }

        val verifyEmailCredentials = VerifyEmailCredentials(email, code)

        return authenticationRepository.verifyEmail(verifyEmailCredentials)
    }

}