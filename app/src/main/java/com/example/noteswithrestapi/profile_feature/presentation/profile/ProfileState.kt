package com.example.noteswithrestapi.profile_feature.presentation.profile

import com.example.noteswithrestapi.profile_feature.domain.model.user.User

data class ProfileState(
    val user: User? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
