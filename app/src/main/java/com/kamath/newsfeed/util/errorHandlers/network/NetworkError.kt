package com.kamath.newsfeed.util.errorHandlers.network

data class NetworkError(
    val apiError: ApiError,
    val throwable: Throwable? = null
)
