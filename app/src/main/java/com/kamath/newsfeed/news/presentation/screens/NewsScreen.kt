package com.kamath.newsfeed.news.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.newsfeed.news.presentation.viewmodels.NewsScreenEvent
import com.kamath.newsfeed.news.presentation.viewmodels.NewsScreenState
import com.kamath.newsfeed.news.presentation.viewmodels.NewsScreenViewmodel
import kotlinx.coroutines.launch

@Composable
internal fun NewsScreen(
    viewmodel: NewsScreenViewmodel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState()}
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewmodel.newsScreenEvents.collect { event ->
            when(event){
                is NewsScreenEvent.ShowSnackBar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }
    NewsScreenContent(uiState,snackbarHostState)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewsScreenContent(
    state: NewsScreenState,
    snackbarHostState: SnackbarHostState) {
    Scaffold(
        topBar = {
            TopAppBar({
                Text("NewsFeed")
            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is NewsScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
                }
                is NewsScreenState.Success -> {
                    LazyColumn{
                        items(state.articles){article ->
                            Text(article.title)
                        }
                    }
                }
                is NewsScreenState.Error -> {
                    Text("There has been issue")
                }
            }
        }
    }
}