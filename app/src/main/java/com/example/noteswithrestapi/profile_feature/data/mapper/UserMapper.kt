package com.example.noteswithrestapi.profile_feature.data.mapper

import com.example.noteswithrestapi.profile_feature.data.remote.dto.GetUserResultDto
import com.example.noteswithrestapi.profile_feature.domain.model.user.User


fun GetUserResultDto.toUser(): User = User(this.userData.email, this.userData.date_created)