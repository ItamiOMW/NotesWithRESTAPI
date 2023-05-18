package com.example.noteswithrestapi.note_feature.domain.usecase

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.domain.model.Note
import com.example.noteswithrestapi.note_feature.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int): AppResponse<Note> {
        return noteRepository.getNote(noteId)
    }

}