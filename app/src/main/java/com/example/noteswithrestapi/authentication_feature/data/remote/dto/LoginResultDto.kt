package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResultDto(
    @SerializedName("data") val loginResultDataDto: LoginResultDataDto?,
    @SerializedName("message") val message: String?
)