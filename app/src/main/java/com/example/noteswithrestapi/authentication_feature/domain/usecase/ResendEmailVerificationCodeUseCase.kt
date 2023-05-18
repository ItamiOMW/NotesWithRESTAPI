package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.ResendEmailVerificationCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import javax.inject.Inject

class ResendEmailVerificationCodeUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String): AppResponse<Unit> {
        if (email.isEmpty()) {
            return AppResponse.failed(AuthenticationAppError.EmptyEmailError)
        }

        val resendEmailVerificationCredentials = ResendEmailVerificationCredentials(email)

        return authenticationRepository.resendEmailVerification(resendEmailVerificationCredentials)
    }

}