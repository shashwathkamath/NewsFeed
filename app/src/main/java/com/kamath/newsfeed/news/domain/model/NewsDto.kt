package com.kamath.newsfeed.news.domain.model

data class NewsDto(
    val source:Source,
    val author:String,
    val title:String,
    val description:String? = null,
    val url:String,
    val urlToImage:String? = null,
    val publishedAt:String,
    val content:String? = null
)

data class Source(
    val id:String,
    val name:String
)
