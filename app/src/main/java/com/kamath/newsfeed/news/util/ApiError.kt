package com.kamath.newsfeed.news.util

enum class ApiError(val error:String) {
    IO_ERROR("IO error"),
    CLIENT_ERROR("Client Error"),
    SERVER_ERROR("Server Error"),
    UNKNOWN_ERROR("Unknown Error")
}