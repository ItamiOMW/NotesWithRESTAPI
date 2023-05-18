package com.example.noteswithrestapi.note_feature.presentation.search_note

import com.example.noteswithrestapi.note_feature.domain.model.Note

data class SearchNoteState(
    val searchQueryInputValue: String = "",

    val searchItems: List<Note> = emptyList(),

    val isLoadingNextItems: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 1,

    val errorMessage: String? = null,
) {
}