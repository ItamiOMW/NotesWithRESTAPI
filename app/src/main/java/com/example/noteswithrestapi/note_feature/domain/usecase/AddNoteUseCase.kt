package com.example.noteswithrestapi.note_feature.domain.usecase

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.domain.error.NoteError
import com.example.noteswithrestapi.note_feature.domain.model.Note
import com.example.noteswithrestapi.note_feature.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(title: String, content: String): AppResponse<Note> {
        if (title.isEmpty()) {
            return AppResponse.failed(NoteError.EmptyTitleError)
        }
        if (content.isEmpty()) {
            return AppResponse.failed(NoteError.EmptyContentError)
        }

        val note = Note(
            title = title,
            content = content
        )

        return noteRepository.addNote(note)
    }

}