package com.kamath.newsfeed

import LoginScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kamath.newsfeed.news.presentation.screens.NewsScreen
import com.kamath.newsfeed.ui.theme.NewsFeedTheme
import com.kamath.newsfeed.util.Destinations
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
                    AppScreen()
                }
            }
        }
    }
}

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.LOGIN_SCREEN) {
        composable(Destinations.LOGIN_SCREEN) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME_SCREEN)
                }
            )
        }
        composable(Destinations.HOME_SCREEN) {
            NewsScreen()
        }
    }
}