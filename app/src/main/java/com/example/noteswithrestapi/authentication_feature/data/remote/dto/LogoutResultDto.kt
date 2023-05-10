package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LogoutResultDto(
    @SerializedName("message") val message: String?
)