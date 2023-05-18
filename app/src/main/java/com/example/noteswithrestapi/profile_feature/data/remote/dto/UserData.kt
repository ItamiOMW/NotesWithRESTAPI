package com.example.noteswithrestapi.profile_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("email") val email: String,
    @SerializedName("date_created") val date_created: String,
)