package com.example.noteswithrestapi.note_feature.presentation.add_note

sealed class AddNoteUiEvent {

    object OnNoteSaved: AddNoteUiEvent()

    data class OnShowSnackbar(val message: String): AddNoteUiEvent()

}
