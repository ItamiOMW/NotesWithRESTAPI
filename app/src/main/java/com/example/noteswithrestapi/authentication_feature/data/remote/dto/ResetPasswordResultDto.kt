package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordResultDto(
    @SerializedName("message") val message: String?
)
