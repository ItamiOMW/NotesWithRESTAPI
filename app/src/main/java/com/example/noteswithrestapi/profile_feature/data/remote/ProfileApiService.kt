package com.example.noteswithrestapi.profile_feature.data.remote

import com.example.noteswithrestapi.profile_feature.data.remote.dto.GetUserResultDto
import com.example.noteswithrestapi.profile_feature.data.remote.dto.LogoutResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProfileApiService {

    @GET("accounts/login/")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): Response<GetUserResultDto>


    @POST("accounts/logout/")
    suspend fun logout(
        @Header("Authorization") token: String,
    ): Response<LogoutResultDto>

}