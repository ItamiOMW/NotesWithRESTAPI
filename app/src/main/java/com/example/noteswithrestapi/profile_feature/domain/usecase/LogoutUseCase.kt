package com.example.noteswithrestapi.profile_feature.domain.usecase

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.profile_feature.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(): AppResponse<Unit> {
        return profileRepository.logout()
    }

}