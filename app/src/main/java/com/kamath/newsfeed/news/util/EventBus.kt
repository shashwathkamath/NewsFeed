package com.kamath.newsfeed.news.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object EventBus {
    private val _events = Channel<Any?>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvents(event:Any){
        _events.send(event)
    }
}

sealed interface NewsBusEvent{
    data class Toast(val message:String): NewsBusEvent
}