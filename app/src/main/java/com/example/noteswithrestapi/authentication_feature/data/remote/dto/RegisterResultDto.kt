package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterResultDto(
    @SerializedName("data") val registerResultDataDto: RegisterResultDataDto,
    @SerializedName("message") val message: String
)