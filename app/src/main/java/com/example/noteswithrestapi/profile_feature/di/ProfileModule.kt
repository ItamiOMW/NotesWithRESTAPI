package com.example.noteswithrestapi.profile_feature.di

import com.example.noteswithrestapi.profile_feature.data.remote.ProfileApiService
import com.example.noteswithrestapi.profile_feature.data.repository.ProfileRepositoryImpl
import com.example.noteswithrestapi.profile_feature.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProfileModule {

    @Binds
    @Singleton
    fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository


    companion object {

        @Provides
        @Singleton
        fun provideProfileApiService(
            retrofit: Retrofit,
        ): ProfileApiService {
            return retrofit.create(ProfileApiService::class.java)
        }

    }


}