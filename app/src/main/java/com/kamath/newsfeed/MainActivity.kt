package com.kamath.newsfeed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kamath.newsfeed.login.presentation.screens.LoginScreen
import com.kamath.newsfeed.news.presentation.screens.NewsScreen
import com.kamath.newsfeed.ui.theme.NewsFeedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsFeedTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    //NewsScreen()
                    LoginScreen()
                }
            }
        }
    }
}