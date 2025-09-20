package com.kamath.newsfeed.util.errorHandlers.network

import retrofit2.HttpException

fun Throwable.toNetworkError(): NetworkError{
    val error = when(this){
        is HttpException -> {
            when(this.code()){
                in 400..499 -> ApiError.BAD_CREDENTIALS
                in 500..599 -> ApiError.SERVER_ERROR
                else -> ApiError.UNKOWN_EXCEPTION
            }
        }
        else -> ApiError.IO_EXCEPTION
    }

    return NetworkError(
        error,
        this
    )
}