package com.example.noteswithrestapi.note_feature.data.remote

import com.example.noteswithrestapi.note_feature.data.remote.dto.GetNotesResultDto
import com.example.noteswithrestapi.note_feature.data.remote.dto.NoteDto
import com.example.noteswithrestapi.note_feature.domain.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NoteApiService {

    @GET("api/v1/list-note/")
    suspend fun getNotes(
        @Header("Authorization") token: String,
        @Query("search") query: String = "",
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
    ): Response<GetNotesResultDto>


    @GET("api/v1/get-note/{pk}")
    suspend fun getNote(
        @Header("Authorization") token: String,
        @Path("pk") id: Int,
    ): Response<NoteDto>


    @POST("api/v1/add-note/")
    suspend fun addNote(
        @Header("Authorization") token: String,
        @Body note: Note
    ): Response<NoteDto>


    @PUT("api/v1/update-note/{pk}")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Path("pk") id: Int,
        @Body note: Note
    ): Response<NoteDto>


    @DELETE("api/v1/delete-note/{pk}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("pk") id: Int,
    ): Response<Unit>

}