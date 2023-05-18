package com.example.noteswithrestapi.note_feature.presentation.notes

import com.example.noteswithrestapi.note_feature.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val page: Int = 1,
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val errorMessage: String? = null,
)
