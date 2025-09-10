package com.kamath.newsfeed.news.util

data class NetworkError(
    val error: ApiError,
    val throwable: Throwable? = null
)
