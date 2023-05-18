package com.example.noteswithrestapi.profile_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetUserResultDto(
    @SerializedName("data") val userData: UserData,
    val message: String?
)