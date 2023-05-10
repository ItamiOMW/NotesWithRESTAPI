package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class IsAuthenticatedResultDto(
    @SerializedName("data") val isAuthenticatedResultDataDto: IsAuthenticatedResultDataDto,
    val message: String
)