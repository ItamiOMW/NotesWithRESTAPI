package com.example.noteswithrestapi.note_feature.presentation.add_note

sealed class AddNoteEvent {

    data class OnTitleInputValueChange(val newValue: String): AddNoteEvent()

    data class OnContentInputValueChange(val newValue: String): AddNoteEvent()

    object OnSaveClick: AddNoteEvent()

}
