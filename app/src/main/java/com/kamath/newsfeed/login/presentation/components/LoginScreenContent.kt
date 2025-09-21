package com.kamath.newsfeed.login.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenState

@Composable
fun LoginScreenContent(
    modifier: Modifier,
    uiState: LoginScreenState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {

    when (uiState) {
        is LoginScreenState.Input -> {
            LoginComponent(
                modifier = modifier,
                uiState = uiState,
                onUsernameChange = onUsernameChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginClick
            )
        }

        is LoginScreenState.Loading -> {
            Box(modifier = Modifier.height(100.dp)) {
                CircularProgressIndicator()
            }
        }
    }
}