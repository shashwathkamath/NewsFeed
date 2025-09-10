package com.kamath.newsfeed.news.domain.repository

import arrow.core.Either
import com.kamath.newsfeed.news.domain.model.NewsResponse
import com.kamath.newsfeed.news.util.NetworkError


interface NewsRepository {
    suspend fun getNews(): Either<NetworkError, NewsResponse>
}