package com.kamath.newsfeed.login.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenEvent
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenState
import com.kamath.newsfeed.login.presentation.viewmodels.LoginViewModel
import timber.log.Timber

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    LoginScreenContent(
        uiState,
        onUsernameChanged = {
            loginViewModel.onEvent(
                LoginScreenEvent.onUsernameChanged(it))
        },
        onPasswordChanged = {
            loginViewModel.onEvent(
                LoginScreenEvent.onPasswordChanged(it)
            )
        },
        onLoginButtonClicked = {
            Timber.d("Inside internal function")
            loginViewModel.onEvent(
                LoginScreenEvent.onLoginClicked
            )
        }
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginScreenState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged:(String) -> Unit,
    onLoginButtonClicked:() -> Unit
) {
    when (uiState) {
        is LoginScreenState.Input -> {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = uiState.username,
                    onValueChange = { onUsernameChanged(it) },
                    label = { Text("Enter Username") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = uiState.password,
                    onValueChange = {onPasswordChanged(it)},
                    label = {Text("Enter Password")}
                )
                Spacer(modifier= Modifier.height(8.dp))
                Button(
                    onClick = {
                        Timber.d("Button is pressed")
                        onLoginButtonClicked()
                    },
                    enabled = uiState.isButtonEnabled
                ) {
                    Text("Login")
                }
            }
        }

        is LoginScreenState.Loading -> {
            Box(modifier =
                Modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is LoginScreenState.Success -> {}
        is LoginScreenState.Error -> {}
    }
}


@Preview(showBackground = false)
@Composable
fun LoginScreenContentPreview() {
    val previewState = LoginScreenState.Input(
        username = "demo",
        password = "",
        isButtonEnabled = false
    )
    LoginScreenContent(
        previewState,
        {},
        {},
        {}
    )
}