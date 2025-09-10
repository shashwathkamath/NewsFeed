package com.kamath.newsfeed.news.util

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): NetworkError{
    val error = when(this){
        is IOException -> ApiError.IO_ERROR
        is HttpException -> {
            when(this.code()){
                in 400..499 -> ApiError.CLIENT_ERROR
                in 500..599 -> ApiError.SERVER_ERROR
                else -> ApiError.UNKNOWN_ERROR
            }
        }
        else -> ApiError.UNKNOWN_ERROR
    }

    return NetworkError(error,this)
}