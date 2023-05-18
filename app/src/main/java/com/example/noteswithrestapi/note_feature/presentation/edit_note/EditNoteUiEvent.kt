package com.example.noteswithrestapi.note_feature.presentation.edit_note

sealed class EditNoteUiEvent {

    object OnNoteSaved: EditNoteUiEvent()

    object OnNoteDeleted: EditNoteUiEvent()

    data class OnShowSnackbar(val message: String): EditNoteUiEvent()

}
