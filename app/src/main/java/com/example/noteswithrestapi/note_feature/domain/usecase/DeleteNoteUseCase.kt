package com.example.noteswithrestapi.note_feature.domain.usecase

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(id: Int): AppResponse<Unit> {
        return noteRepository.deleteNote(id)
    }

}