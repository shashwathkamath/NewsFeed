package com.kamath.newsfeed.login.domain.repository

import arrow.core.Either
import com.kamath.newsfeed.login.domain.model.LoginRequest
import com.kamath.newsfeed.login.domain.model.LoginResponse
import com.kamath.newsfeed.util.errorHandlers.network.NetworkError

interface LoginRepository {
    suspend fun login(request: LoginRequest): Either<NetworkError, LoginResponse>
}