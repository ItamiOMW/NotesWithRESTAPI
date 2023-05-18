package com.example.noteswithrestapi.note_feature.presentation.add_note

data class AddNoteState(
    val titleInputValue: String = "",

    val contentInputValue: String = "",

    val isLoading: Boolean = false,
)
