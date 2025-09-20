import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenEvent
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenState
import com.kamath.newsfeed.login.presentation.viewmodels.LoginTransitionEvent
import com.kamath.newsfeed.login.presentation.viewmodels.LoginViewModel
import timber.log.Timber


@Composable
internal fun LoginScreen(
    viewmodel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewmodel.eventFlow, snackbarHostState) {
        viewmodel.eventFlow.collect { event ->
            when (event) {
                is LoginTransitionEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is LoginTransitionEvent.NavigateToHome -> {
                    onNavigateToHome()
                }
            }
        }
    }
    Scaffold(
        topBar = { Text("Login") },
        snackbarHost = { snackbarHostState }
    ) { paddingValues ->
        LoginScreenContent(
            modifier = Modifier.padding(paddingValues),
            uiState,
            onUsernameChange = {
                viewmodel.onEvent(
                    LoginScreenEvent.OnUsernameChange(it)
                )
            },
            onPasswordChange = {
                viewmodel.onEvent(
                    LoginScreenEvent.OnPasswordChange(it)
                )
            },
            onLoginClick = {
                Timber.d("Button got pressed!!")
                viewmodel.onEvent(
                    LoginScreenEvent.OnButtonClick
                )
            }
        )

    }
}

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

        is LoginScreenState.Success -> {

        }

        is LoginScreenState.Loading -> {
            Box(modifier = Modifier.height(100.dp)) {
                CircularProgressIndicator()
            }
        }

        is LoginScreenState.Error -> {}
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    val uiState = LoginScreenState.Input(
//        username = "",
//        password = "",
//        isButtonEnabled = false
//    )
//    LoginScreenContent(
//        uiState,
//        {},
//        {}, onLoginClick = {})
//}