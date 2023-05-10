package com.example.noteswithrestapi.authentication_feature.di

import com.example.noteswithrestapi.authentication_feature.data.remote.AccountsApiService
import com.example.noteswithrestapi.authentication_feature.data.repository.AuthenticationRepositoryImpl
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticationModule {


    @Binds
    @Singleton
    fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository


    companion object {

        @Provides
        @Singleton
        fun provideAccountsApiService(
            retrofit: Retrofit
        ): AccountsApiService {
            return retrofit.create(AccountsApiService::class.java)
        }

    }



}