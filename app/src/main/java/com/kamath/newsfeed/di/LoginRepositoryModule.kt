package com.kamath.newsfeed.di

import com.kamath.newsfeed.login.data.repository.LoginRepositoryImpl
import com.kamath.newsfeed.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule{

    @Binds
    @Singleton
    abstract fun providesLoginRepositoryModule(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}