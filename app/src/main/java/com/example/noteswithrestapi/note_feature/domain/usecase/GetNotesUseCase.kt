package com.example.noteswithrestapi.note_feature.domain.usecase

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.domain.model.Note
import com.example.noteswithrestapi.note_feature.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(page: Int, query: String = ""): AppResponse<List<Note>> {
        return noteRepository.getNotes(page, query)
    }

}