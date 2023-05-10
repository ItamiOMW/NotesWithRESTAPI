package com.example.noteswithrestapi.authentication_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordConfirmResultDto(
    @SerializedName("message") val message: String?
)
