package com.kamath.newsfeed.login.data.repository

import arrow.core.Either
import com.kamath.newsfeed.login.data.remote.LoginApi
import com.kamath.newsfeed.login.domain.model.LoginRequest
import com.kamath.newsfeed.login.domain.model.LoginResponse
import com.kamath.newsfeed.login.domain.repository.LoginRepository
import com.kamath.newsfeed.util.errorHandlers.network.NetworkError
import com.kamath.newsfeed.util.errorHandlers.network.toNetworkError

import jakarta.inject.Inject
import timber.log.Timber

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
): LoginRepository {
    override suspend fun login(request: LoginRequest): Either<NetworkError, LoginResponse> {
        return Either.catch {
            Timber.d("Inside LoginRepo ${request.username}, ${request.password}")
            val response = loginApi.login(request)
            Timber.d("Inside LoginRepo $response")
            response
        }.mapLeft {
            Timber.d(it)
            it.toNetworkError()
        }
    }
}