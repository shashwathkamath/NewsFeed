import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.newsfeed.login.presentation.components.LoginScreenContent
import com.kamath.newsfeed.login.presentation.viewmodels.LoginScreenEvent
import com.kamath.newsfeed.login.presentation.viewmodels.LoginTransitionEvent
import com.kamath.newsfeed.login.presentation.viewmodels.LoginViewModel


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
        topBar = {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxSize()
            ) {
                Text("Login")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.padding(top = 20.dp)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
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
                    viewmodel.onEvent(
                        LoginScreenEvent.OnButtonClick
                    )
                }
            )
        }
    }
}