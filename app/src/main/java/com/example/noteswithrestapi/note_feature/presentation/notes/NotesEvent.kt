package com.example.noteswithrestapi.note_feature.presentation.notes

sealed class NotesEvent {

    object OnLoadNextItems: NotesEvent()

    object OnRefresh: NotesEvent()

    object OnRetry: NotesEvent()

}
