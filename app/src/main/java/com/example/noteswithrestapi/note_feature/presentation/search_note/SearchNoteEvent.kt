package com.example.noteswithrestapi.note_feature.presentation.search_note


sealed class SearchNoteEvent() {

    data class OnSearchQueryValueChange(val newValue: String) : SearchNoteEvent()

    object OnClearQuery : SearchNoteEvent()

    object OnLoadNextItems: SearchNoteEvent()

    object OnRetryLoadNextItems: SearchNoteEvent()

    object OnRefresh: SearchNoteEvent()

}