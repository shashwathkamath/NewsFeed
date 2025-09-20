package com.kamath.newsfeed.di

import com.kamath.newsfeed.news.data.repository.NewsRepositoryImpl
import com.kamath.newsfeed.news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsRepositoryModule {
    @Binds
    @Singleton
    abstract fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}