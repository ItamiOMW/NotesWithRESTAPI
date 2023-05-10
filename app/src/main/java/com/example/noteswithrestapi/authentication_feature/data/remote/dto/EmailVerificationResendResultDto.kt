package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmailVerificationResendResultDto(
    @SerializedName("data") val emailVerificationResendResultDataDto: EmailVerificationResendResultDataDto?,
    @SerializedName("message") val message: String?
)