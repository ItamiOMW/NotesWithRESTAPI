package com.example.noteswithrestapi.core.domain.model


sealed class AppResponse<out T> {

    //Success state
    data class Success<T>(val data: T) : AppResponse<T>()

    //Failed state
    data class Failed<T>(val error: AppError) : AppResponse<T>()


    companion object {

        //GET SUCCESS STATE
        fun <T> success(data: T) = Success(data)

        //GET FAILED STATE
        fun <T> failed(error: AppError) = Failed<T>(error)

    }

}
