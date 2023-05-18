package com.example.noteswithrestapi.note_feature.data.remote.dto

data class NoteDto(
    val id: Int,
    val user_id: Int,
    val title: String,
    val content: String,
    val date_updated: String,
)