package com.kamath.newsfeed.login.domain.model

data class LoginResponse(
    val accessToken:String,
    val refreshToken:String,
    val id:Int,
    val username:String,
    val email:String,
    val firstName:String,
    val lastName:String,
    val gender:String,
    val image:String?
)
