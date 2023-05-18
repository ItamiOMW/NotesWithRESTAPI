package com.example.noteswithrestapi.note_feature.domain.model

import com.example.noteswithrestapi.core.utils.Constants

data class Note(
    val id: Int = Constants.UNKNOWN_ID,
    val title: String,
    val content: String,
    val date_updated: String = "",
)
