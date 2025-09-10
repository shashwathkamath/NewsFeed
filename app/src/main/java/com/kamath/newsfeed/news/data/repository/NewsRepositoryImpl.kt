package com.kamath.newsfeed.news.data.repository

import arrow.core.Either
import com.kamath.newsfeed.news.data.remote.NewsApi
import com.kamath.newsfeed.news.domain.model.NewsResponse
import com.kamath.newsfeed.news.domain.repository.NewsRepository
import com.kamath.newsfeed.news.util.NetworkError
import com.kamath.newsfeed.news.util.toNetworkError
import jakarta.inject.Inject
import timber.log.Timber

class NewsRepositoryImpl@Inject constructor(
    private val newsApi: NewsApi
): NewsRepository {
    override suspend fun getNews(): Either<NetworkError, NewsResponse> {
        return Either.catch {
            val response = newsApi.getNews()
            Timber.d("${response.articles}")
            response
        }.mapLeft { it.toNetworkError() }
    }
}