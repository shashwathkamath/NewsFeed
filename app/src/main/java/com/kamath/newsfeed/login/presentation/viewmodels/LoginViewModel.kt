package com.kamath.newsfeed.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamath.newsfeed.login.domain.model.LoginRequest
import com.kamath.newsfeed.login.domain.repository.LoginRepository
import com.kamath.newsfeed.util.errorHandlers.network.ApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed class LoginScreenState {
    object Loading : LoginScreenState()
    data class Input(
        val username: String,
        val password: String,
        val isButtonEnabled: Boolean,
        val errorMessage: String? = null
    ) : LoginScreenState()
}

sealed class LoginScreenEvent {
    data class OnUsernameChange(val username: String) : LoginScreenEvent()
    data class OnPasswordChange(val password: String) : LoginScreenEvent()
    object OnButtonClick : LoginScreenEvent()
}

sealed class LoginTransitionEvent {
    data class ShowSnackBar(val message: String) : LoginTransitionEvent()
    object NavigateToHome : LoginTransitionEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginScreenState>(
        LoginScreenState.Input(
            username = "",
            password = "",
            isButtonEnabled = false
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginTransitionEvent>(replay = 0)
    val eventFlow = _eventFlow.asSharedFlow()
    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnUsernameChange -> {
                val currentState = _uiState.value
                if (currentState is LoginScreenState.Input) {
                    val username = event.username
                    val buttonEnabled = username.isNotEmpty() && currentState.password.isNotBlank()
                    _uiState.value = currentState.copy(
                        username = username,
                        isButtonEnabled = buttonEnabled
                    )
                }
            }

            is LoginScreenEvent.OnPasswordChange -> {
                val currentState = _uiState.value
                if (currentState is LoginScreenState.Input) {
                    val password = event.password
                    val buttonEnabled = password.isNotEmpty() && currentState.username.isNotEmpty()
                    _uiState.value = currentState.copy(
                        password = password,
                        isButtonEnabled = buttonEnabled
                    )
                }
            }

            is LoginScreenEvent.OnButtonClick -> {
                val currentState = _uiState.value
                if (currentState is LoginScreenState.Input && currentState.isButtonEnabled) {
                    Timber.d("%s%s", currentState.username, currentState.password)
                    viewModelScope.launch {
                        _uiState.value = LoginScreenState.Loading
                        repository.login(
                            LoginRequest(
                                currentState.username,
                                currentState.password
                            )
                        )
                            .onRight { response ->
                                _eventFlow.emit(LoginTransitionEvent.NavigateToHome)
                            }
                            .onLeft {
                                val errorMsg = it.apiError.error
                                _uiState.value = LoginScreenState.Input(
                                    username = "",
                                    password = "",
                                    isButtonEnabled = false,
                                    errorMessage = errorMsg
                                )
                                _eventFlow.emit(LoginTransitionEvent.ShowSnackBar(errorMsg))
                            }
                    }
                }
            }
        }
    }
}