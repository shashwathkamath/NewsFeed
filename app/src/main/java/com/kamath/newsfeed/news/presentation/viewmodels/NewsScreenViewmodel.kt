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
    data class Success(val articles: List<NewsDto>) : NewsScreenState()
    data class Error(val errorMessage: String) : NewsScreenState()
}

sealed class NewsScreenEvent {
    data class ShowSnackBar(val message: String) : NewsScreenEvent()
    //lets create an event to navigate to detail screen
}


@HiltViewModel
class NewsScreenViewmodel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsScreenEvents = MutableSharedFlow<NewsScreenEvent>()
    val newsScreenEvents = _newsScreenEvents.asSharedFlow()

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
                    val errorMessage = when (it.apiError) {
                        ApiError.BAD_CREDENTIALS -> "Invalid credentials"
                        ApiError.SERVER_ERROR -> "Server issue"
                        ApiError.IO_EXCEPTION -> "Something went wrong"
                        ApiError.UNKOWN_EXCEPTION -> ApiError.UNKOWN_EXCEPTION.error
                    }
                    _uiState.value = NewsScreenState.Error(errorMessage)
                    _newsScreenEvents.emit(NewsScreenEvent.ShowSnackBar("Error occured due to $errorMessage"))
                }
        }
    }
}