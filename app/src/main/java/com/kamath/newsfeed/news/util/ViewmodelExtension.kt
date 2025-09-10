package com.kamath.newsfeed.news.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event:Any){
    viewModelScope.launch {
        EventBus.sendEvents(event)
    }
}