package com.kamath.newsfeed.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamath.newsfeed.login.domain.model.LoginRequest
import com.kamath.newsfeed.login.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LoginScreenState {
    object Loading : LoginScreenState()
    data class Input(
        val username: String,
        val password: String,
        val isButtonEnabled: Boolean
    ) : LoginScreenState()

    data class Success(val message: String) : LoginScreenState()
    data class Error(val message: String) : LoginScreenState()
}

sealed class LoginScreenEvent {
    data class onUsernameChanged(val username: String) : LoginScreenEvent()
    data class onPasswordChanged(val password: String) : LoginScreenEvent()
    object onLoginClicked : LoginScreenEvent()
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

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.onUsernameChanged -> {
                val current = _uiState.value
                if (current is LoginScreenState.Input) {
                    val newUsername = event.username
                    val buttonEnabled = newUsername.isNotBlank() && current.password.isNotBlank()
                    _uiState.value =
                        current.copy(username = newUsername, isButtonEnabled = buttonEnabled)
                }
            }

            is LoginScreenEvent.onPasswordChanged -> {
                val current = _uiState.value
                if (current is LoginScreenState.Input) {
                    val newPassword = event.password
                    val buttonEnabled = newPassword.isNotBlank() && current.username.isNotBlank()
                    _uiState.value =
                        current.copy(password = newPassword, isButtonEnabled = buttonEnabled)
                }
            }

            is LoginScreenEvent.onLoginClicked -> {
                val current = _uiState.value
                if (current is LoginScreenState.Input && current.isButtonEnabled) {
                    viewModelScope.launch {
                        _uiState.value = LoginScreenState.Loading
                        repository.login(
                            LoginRequest(
                                current.username,
                                current.password
                            )
                        )
                            .onRight {
                                _uiState.value = LoginScreenState.Success("Login Successful for ${it.username}")
                            }.onLeft {
                                _uiState.value = LoginScreenState.Error("Credentials did not match or some error ${it.error}")
                            }
                    }
                }
            }
        }
    }

}