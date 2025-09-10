package com.kamath.newsfeed.news.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamath.newsfeed.news.domain.model.NewsDto
import com.kamath.newsfeed.news.domain.repository.NewsRepository
import com.kamath.newsfeed.news.util.NewsBusEvent
import com.kamath.newsfeed.news.util.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewsScreenState {
    object Loading : NewsScreenState()
    data class Success(val articles: List<NewsDto>) : NewsScreenState()
    object Error : NewsScreenState()
}

@HiltViewModel
class NewsScreenViewmodel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsScreenState>(NewsScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        NewsScreenState.Loading
        viewModelScope.launch {
            newsRepository.getNews()
                .onRight {
                    val listOfArticles = it.articles
                    _uiState.value = NewsScreenState.Success(listOfArticles)
                }
                .onLeft {
                    sendEvent(NewsBusEvent.Toast("Some error occurred ${it.error.error}"))
                }
        }
    }
}