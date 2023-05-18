package com.example.noteswithrestapi.note_feature.di

import com.example.noteswithrestapi.note_feature.data.remote.NoteApiService
import com.example.noteswithrestapi.note_feature.data.repository.NoteRepositoryImpl
import com.example.noteswithrestapi.note_feature.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NoteModule {

    @Binds
    @Singleton
    fun bindNoteRepository(
        noteRepository: NoteRepositoryImpl,
    ): NoteRepository


    companion object {

        @Provides
        @Singleton
        fun provideNoteApiService(
            retrofit: Retrofit,
        ): NoteApiService {
            return retrofit.create(NoteApiService::class.java)
        }

    }

}