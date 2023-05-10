package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.ResendEmailVerificationCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import javax.inject.Inject

class ResendEmailVerificationCodeUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String): Response<Unit> {
        if (email.isEmpty()) {
            return Response.failed(AppError.EmptyEmailError)
        }

        val resendEmailVerificationCredentials = ResendEmailVerificationCredentials(email)

        return authenticationRepository.resendEmailVerification(resendEmailVerificationCredentials)
    }

}