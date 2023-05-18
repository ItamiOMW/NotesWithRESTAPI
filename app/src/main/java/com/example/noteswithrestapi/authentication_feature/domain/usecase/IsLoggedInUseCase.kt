package com.example.noteswithrestapi.authentication_feature.domain.usecase

import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.domain.model.AppResponse
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(): AppResponse<Boolean> {
        return authenticationRepository.isLoggedIn()
    }

}