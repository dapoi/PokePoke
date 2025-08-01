package com.project.compose.feature.auth.screen

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.compose.core.common.base.BaseScreen
import com.project.compose.core.common.ui.theme.Dimens.Dp16
import com.project.compose.core.common.ui.theme.Dimens.Dp2
import com.project.compose.core.common.ui.theme.Dimens.Dp24
import com.project.compose.core.common.ui.theme.Dimens.Dp8
import com.project.compose.core.common.ui.theme.PokeTheme.typography
import com.project.compose.core.common.utils.LocalActivity
import com.project.compose.core.common.utils.state.collectAsStateValue
import com.project.compose.core.data.source.local.model.UserEntity
import com.project.compose.core.navigation.helper.navigateTo
import com.project.compose.core.navigation.route.AuthGraph.AuthRoute
import com.project.compose.core.navigation.route.HomeGraph.HomeLandingRoute
import com.project.compose.feature.auth.screen.attr.AuthScreenAttr.AuthMode.LOGIN
import com.project.compose.feature.auth.screen.attr.AuthScreenAttr.AuthMode.REGISTER
import com.project.compose.feature.auth.viewmodel.AuthViewModel

@Composable
internal fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) = with(viewModel) {
    var authMode by remember { mutableStateOf(LOGIN) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf("") }
    val authState = authState.collectAsStateValue()
    val context = LocalContext.current
    val activity = LocalActivity.current

    val title = if (authMode == LOGIN) "Login" else "Register"
    val buttonText = if (authMode == LOGIN) "Login" else "Register"
    val switcherText = if (authMode == LOGIN) "Don't have an account? Register"
    else "Already have an account? Login"

    BaseScreen(clipToTopBar = true) {
        LaunchedEffect(authState) {
            authState.handleUiState(
                onLoading = { showLoading = true },
                onSuccess = {
                    showLoading = false
                    navController.navigateTo(
                        route = HomeLandingRoute,
                        popUpTo = AuthRoute::class,
                        inclusive = true,
                        launchSingleTop = true
                    )
                    resetAuthState()
                },
                onFailed = {
                    showLoading = false
                    showError = it.message ?: "Login failed"
                    resetAuthState()
                }
            )
        }

        LaunchedEffect(showError) {
            if (showError.isNotBlank()) Toast.makeText(
                context,
                showError,
                LENGTH_SHORT
            ).show()
            showError = ""
        }

        BackHandler { activity.finish() }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Text(title, style = typography.h2)
            Spacer(modifier = Modifier.height(Dp16))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.height(Dp8))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(Dp16))
            Button(
                onClick = {
                    if (authMode == LOGIN) viewModel.login(username, password)
                    else if (username.isNotBlank() && password.isNotBlank()) {
                        viewModel.register(
                            UserEntity(
                                username = username,
                                password = password
                            )
                        )
                        Toast.makeText(
                            context,
                            "Registration successful, please login",
                            LENGTH_SHORT
                        ).show()
                        authMode = LOGIN
                    }
                },
                enabled = username.isNotBlank() && password.isNotBlank(),
            ) {
                if (showLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(Dp24),
                        strokeWidth = Dp2
                    )
                } else {
                    Text(buttonText)
                }
            }
            TextButton(onClick = {
                authMode = if (authMode == LOGIN) REGISTER else LOGIN
            }) { Text(switcherText) }
        }
    }
}