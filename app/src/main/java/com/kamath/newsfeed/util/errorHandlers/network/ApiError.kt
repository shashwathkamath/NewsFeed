package com.kamath.newsfeed.util.errorHandlers.network

enum class ApiError(val error:String) {
    BAD_CREDENTIALS("Bad Credentials"),
    SERVER_ERROR("Server error"),
    IO_EXCEPTION("IO Exception"),
    UNKOWN_EXCEPTION("Unknown Exception")
}