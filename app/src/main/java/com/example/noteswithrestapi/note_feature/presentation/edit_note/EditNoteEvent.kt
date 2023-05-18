package com.example.noteswithrestapi.note_feature.presentation.edit_note

sealed class EditNoteEvent {

    data class OnTitleInputValueChange(val newValue: String): EditNoteEvent()

    data class OnContentInputValueChange(val newValue: String): EditNoteEvent()

    object OnSaveClick: EditNoteEvent()

    object OnDeleteClick: EditNoteEvent()

}
