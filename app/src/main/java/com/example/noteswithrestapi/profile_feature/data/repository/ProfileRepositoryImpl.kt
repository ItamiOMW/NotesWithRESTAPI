package com.example.noteswithrestapi.profile_feature.data.repository

import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.data.network.getAppErrorByHttpErrorCode
import com.example.noteswithrestapi.core.data.token.TokenManager
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.profile_feature.data.mapper.toUser
import com.example.noteswithrestapi.profile_feature.data.remote.ProfileApiService
import com.example.noteswithrestapi.profile_feature.domain.model.user.User
import com.example.noteswithrestapi.profile_feature.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val profileApiService: ProfileApiService,
) : ProfileRepository {


    //Independently on result remove token and navigate to authentication screen
    override suspend fun logout(): AppResponse<Unit> {
        try {
            val token = tokenManager.authToken ?: return AppResponse.Success(Unit)
            tokenManager.removeToken()
            val result = profileApiService.logout("Token $token")

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_logout_failed))
        }
    }

    override suspend fun getCurrentUser(): AppResponse<User> {
        try {
            val token = tokenManager.authToken
                ?: return AppResponse.failed(AppError.GeneralError(R.string.error_logout_failed))

            val result = profileApiService.getUser("Token $token")

            if (result.isSuccessful) {
                val user = result.body()?.toUser() ?: return AppResponse.failed(AppError.GeneralError(R.string.error_logout_failed))
                return AppResponse.success(user)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_logout_failed))
        }
    }

}