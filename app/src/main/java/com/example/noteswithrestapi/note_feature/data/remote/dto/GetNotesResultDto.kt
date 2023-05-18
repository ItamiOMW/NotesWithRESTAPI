package com.example.noteswithrestapi.note_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetNotesResultDto(
    val count: Int,
    val next: String,
    val previous: Any,
    @SerializedName("results") val noteDtos: List<NoteDto>
)