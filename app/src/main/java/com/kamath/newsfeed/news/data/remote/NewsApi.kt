package com.kamath.newsfeed.news.data.remote

import com.kamath.newsfeed.news.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsApi {
    companion object{
        const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "78f2183c25f94e63a6a97da34defd7e0"
    }

    @Headers("x-api-key:$API_KEY")
    @GET("v2/top-headlines?country=us&pageSize=100")
    suspend fun getNews(): NewsResponse
}