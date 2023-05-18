package com.example.noteswithrestapi.note_feature.domain.repository

import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.domain.model.Note

interface NoteRepository {

    suspend fun addNote(note: Note): AppResponse<Note>

    suspend fun deleteNote(noteId: Int): AppResponse<Unit>

    suspend fun updateNote(note: Note): AppResponse<Note>

    suspend fun getNotes(page: Int, query: String): AppResponse<List<Note>>

    suspend fun getNote(id: Int): AppResponse<Note>

}