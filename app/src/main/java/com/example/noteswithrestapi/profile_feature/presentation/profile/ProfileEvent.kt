package com.example.noteswithrestapi.profile_feature.presentation.profile

sealed class ProfileEvent {

    object OnLogout: ProfileEvent()

    object OnRetryLoadProfile: ProfileEvent()

}
