package com.kamath.newsfeed.news.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.newsfeed.news.presentation.components.NewsItem
import com.kamath.newsfeed.news.presentation.viewmodels.NewsScreenEvent
import com.kamath.newsfeed.news.presentation.viewmodels.NewsScreenState
import com.kamath.newsfeed.news.presentation.viewmodels.NewsScreenViewmodel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsScreen(
    viewmodel: NewsScreenViewmodel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewmodel.uiEvent, snackbarHostState) {
        viewmodel.uiEvent.collect { event ->
            when (event) {
                is NewsScreenEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
    NewsScreenContent(uiState, snackbarHostState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenContent(uiState: NewsScreenState, snackbarHostState: SnackbarHostState) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar({ Text("Latest News") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (uiState) {
            is NewsScreenState.Success -> {
                val news = uiState.news
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    items(news) {
                        NewsItem(it, {
                            Timber.d("${it.url} got clicked")
                        })
                    }
                }
            }

            is NewsScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NewsScreenState.Error -> {

            }
        }
    }
}