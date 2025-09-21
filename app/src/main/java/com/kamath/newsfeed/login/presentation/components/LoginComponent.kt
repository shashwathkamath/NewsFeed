package com.kamath.newsfeed.login.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenState

@Composable
fun LoginComponent(
    modifier: Modifier,
    uiState: LoginScreenState.Input,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,

        ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = uiState.username,
                onValueChange = { onUsernameChange(it) },
                placeholder = { Text("Enter Username") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = uiState.password,
                onValueChange = { onPasswordChange(it) },
                placeholder = { Text("Enter Password") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = { onLoginClick() },
                enabled = uiState.isButtonEnabled
            ) {
                Text("Login")
            }
        }
    }
}