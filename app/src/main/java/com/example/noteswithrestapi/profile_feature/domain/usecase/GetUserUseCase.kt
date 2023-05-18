package com.example.noteswithrestapi.profile_feature.domain.usecase

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.profile_feature.domain.model.user.User
import com.example.noteswithrestapi.profile_feature.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(): AppResponse<User> = profileRepository.getCurrentUser()

}