package com.kamath.newsfeed.news.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamath.newsfeed.news.domain.model.NewsDto
import com.kamath.newsfeed.news.domain.repository.NewsRepository
import com.kamath.newsfeed.util.errorHandlers.network.ApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewsScreenState {
    object Loading : NewsScreenState()
    data class Success(val news: List<NewsDto>) : NewsScreenState()
    data class Error(val error: String) : NewsScreenState()
}

sealed class NewsScreenEvent {
    data class ShowSnackBar(val message: String) : NewsScreenEvent()
}

@HiltViewModel
class NewsScreenViewmodel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsScreenState>(NewsScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NewsScreenEvent>(replay = 0)
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsScreenState.Loading
            newsRepository.getNews()
                .onRight {
                    val newsArticles = it.articles
                    _uiState.value = NewsScreenState.Success(newsArticles)
                }
                .onLeft {
                    val errorMessage = it.apiError.error
                    _uiEvent.emit(NewsScreenEvent.ShowSnackBar(errorMessage))
                }
        }
    }
}