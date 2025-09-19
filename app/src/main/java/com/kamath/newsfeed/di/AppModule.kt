package com.kamath.newsfeed.di

import com.kamath.newsfeed.login.data.remote.LoginApi
import com.kamath.newsfeed.news.data.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsApiRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApiRetrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @NewsApiRetrofit
    fun provideNewsRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(@NewsApiRetrofit  retrofit: Retrofit): NewsApi{
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    @AuthApiRetrofit
    fun provideLoginRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(LoginApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApi(@AuthApiRetrofit retrofit: Retrofit): LoginApi{
        return retrofit.create(LoginApi::class.java)
    }
}