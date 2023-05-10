package com.example.noteswithrestapi.core.domain.model

sealed class Response<out T> {

    //Success state
    data class Success<T>(val data: T) : Response<T>()

    //Failed state
    data class Failed<T>(val error: AppError) : Response<T>()


    companion object {

        //GET SUCCESS STATE
        fun <T> success(data: T) = Success(data)

        //GET FAILED STATE
        fun <T> failed(error: AppError) = Failed<T>(error)

    }

}
