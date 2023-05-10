package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.SendPasswordResetCodeCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import javax.inject.Inject

class SendPasswordResetCodeUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String): Response<Unit> {
        if (email.isEmpty()) {
            return Response.failed(AppError.EmptyEmailError)
        }

        val sendPasswordResetCodeCredentials = SendPasswordResetCodeCredentials(email)

        return authenticationRepository.sendPasswordResetCode(sendPasswordResetCodeCredentials)

    }

}