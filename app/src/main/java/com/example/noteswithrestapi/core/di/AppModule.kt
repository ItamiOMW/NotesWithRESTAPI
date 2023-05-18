package com.example.noteswithrestapi.core.di

import com.example.noteswithrestapi.core.data.token.TokenManager
import com.example.noteswithrestapi.core.data.token.TokenManagerEncryptedSharedPreferences
import com.example.noteswithrestapi.core.data.network.NetworkConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindTokenManager(
        tokenManagerEncryptedSharedPreferences: TokenManagerEncryptedSharedPreferences
    ): TokenManager

    companion object {

        @Provides
        @Singleton
        fun provideNoteApiRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(NetworkConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        }

    }

}