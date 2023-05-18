package com.example.noteswithrestapi.note_feature.presentation.edit_note

data class EditNoteState(
    val titleInputValue: String = "",

    val contentInputValue: String = "",

    val isLoading: Boolean = false,
    val getNoteErrorMessage: String? = null,
)
