package com.kamath.newsfeed.login.data.remote

import com.kamath.newsfeed.login.domain.model.LoginRequest
import com.kamath.newsfeed.login.domain.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    companion object{
        val BASE_URL = "https://dummyjson.com/"
    }

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}