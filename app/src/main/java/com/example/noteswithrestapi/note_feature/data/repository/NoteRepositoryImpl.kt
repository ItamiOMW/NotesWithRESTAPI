package com.example.noteswithrestapi.note_feature.data.repository

import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.data.network.getAppErrorByHttpErrorCode
import com.example.noteswithrestapi.core.data.token.TokenManager
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.data.mapper.toNote
import com.example.noteswithrestapi.note_feature.data.remote.NoteApiService
import com.example.noteswithrestapi.note_feature.domain.model.Note
import com.example.noteswithrestapi.note_feature.domain.repository.NoteRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteApiService: NoteApiService,
    private val tokenManager: TokenManager,
) : NoteRepository {

    override suspend fun addNote(note: Note): AppResponse<Note> {
        try {
            val token = tokenManager.authToken
                ?: return AppResponse.failed(AppError.GeneralError(R.string.error_get_notes))

            val result = noteApiService.addNote("Token $token", note)

            if (result.isSuccessful) {
                val noteResult = result.body()?.toNote()
                    ?: return AppResponse.failed(AppError.GeneralError(R.string.error_get_notes))
                return AppResponse.success(noteResult)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_get_notes))
        }
    }


    override suspend fun deleteNote(noteId: Int): AppResponse<Unit> {
        try {

            val token = tokenManager.authToken
                ?: return AppResponse.failed(AppError.GeneralError(R.string.error_delete_note))

            val result = noteApiService.deleteNote("Token $token", noteId)

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_delete_note))
        }
    }


    override suspend fun updateNote(note: Note): AppResponse<Note> {
        try {

            val token = tokenManager.authToken
                ?: return AppResponse.failed(AppError.GeneralError(R.string.error_delete_note))

            val result = noteApiService.updateNote("Token $token", note.id, note)

            if (result.isSuccessful) {
                val noteModel = result.body()?.toNote()
                    ?: return AppResponse.failed(AppError.GeneralError(R.string.error_delete_note))
                return AppResponse.success(noteModel)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_delete_note))
        }
    }


    override suspend fun getNotes(page: Int, query: String): AppResponse<List<Note>> {
        try {

            val token = tokenManager.authToken
                ?: return AppResponse.failed(AppError.GeneralError(R.string.error_get_notes))

            val result = noteApiService.getNotes(
                token = "Token $token",
                page = page,
                query = query,
            )

            if (result.isSuccessful) {
                val noteDtos = result.body()?.noteDtos ?: return AppResponse.success(emptyList())
                val notes = noteDtos.map { dto -> dto.toNote() }
                return AppResponse.success(notes)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_get_notes))
        }
    }


    override suspend fun getNote(id: Int): AppResponse<Note> {
        try {

            val token = tokenManager.authToken
                ?: return AppResponse.failed(AppError.GeneralError(R.string.error_get_notes))

            val result = noteApiService.getNote(token = "Token $token", id = id)

            if (result.isSuccessful) {
                val note = result.body()?.toNote()
                    ?: return AppResponse.failed(AppError.GeneralError(R.string.error_get_note))
                return AppResponse.success(note)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_get_note))
        }
    }

}