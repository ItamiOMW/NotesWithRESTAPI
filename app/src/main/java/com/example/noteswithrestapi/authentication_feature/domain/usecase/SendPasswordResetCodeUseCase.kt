package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.SendPasswordResetCodeCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import javax.inject.Inject

class SendPasswordResetCodeUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String): AppResponse<Unit> {
        if (email.isEmpty()) {
            return AppResponse.failed(AuthenticationAppError.EmptyEmailError)
        }

        val sendPasswordResetCodeCredentials = SendPasswordResetCodeCredentials(email)

        return authenticationRepository.sendPasswordResetCode(sendPasswordResetCodeCredentials)

    }

}