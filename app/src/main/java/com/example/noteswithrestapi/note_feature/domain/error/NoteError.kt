package com.example.noteswithrestapi.note_feature.domain.error

import com.example.noteswithrestapi.core.domain.model.AppError

open class NoteError: AppError() {

    object EmptyTitleError: NoteError()

    object EmptyContentError: NoteError()

}