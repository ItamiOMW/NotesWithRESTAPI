package com.example.noteswithrestapi.profile_feature.domain.repository

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.profile_feature.domain.model.user.User

interface ProfileRepository {

    suspend fun logout(): AppResponse<Unit>

    suspend fun getCurrentUser(): AppResponse<User>

}