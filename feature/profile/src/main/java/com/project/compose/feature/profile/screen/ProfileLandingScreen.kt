package com.project.compose.feature.profile.screen

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.compose.core.common.base.BaseScreen
import com.project.compose.core.common.ui.component.attr.AppTopBarAttr.TopBarArgs
import com.project.compose.core.common.ui.theme.Dimens.Dp16
import com.project.compose.core.common.ui.theme.PokeTheme.typography
import com.project.compose.core.common.utils.state.collectAsStateValue
import com.project.compose.core.data.source.local.model.UserEntity
import com.project.compose.core.navigation.helper.navigateTo
import com.project.compose.core.navigation.route.AuthGraph.AuthRoute
import com.project.compose.core.navigation.route.ProfileGraph.ProfileLandingRoute
import com.project.compose.feature.profile.viewmodel.ProfileLandingViewModel

@Composable
internal fun ProfileLandingScreen(
    navController: NavController,
    viewModel: ProfileLandingViewModel = hiltViewModel()
) = with(viewModel) {
    val profileState = profileState.collectAsStateValue()
    var showLoading by remember { mutableStateOf(false) }
    var dataUser by remember { mutableStateOf<UserEntity?>(null) }
    var showError by remember { mutableStateOf("") }

    BaseScreen(
        topBarArgs = TopBarArgs(
            title = {
                Text(
                    text = "Profile User",
                    style = typography.h3
                )
            }
        )
    ) {
        LaunchedEffect(Unit) { getProfile() }

        LaunchedEffect(profileState) {
            profileState.handleUiState(
                onLoading = { showLoading = true },
                onSuccess = { user ->
                    showLoading = false
                    dataUser = user
                },
                onFailed = { error ->
                    showLoading = false
                    showError = error.message ?: "An error occurred"
                }
            )
        }

        when {
            showLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            showError.isNotEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = showError,
                    style = typography.body1
                )
            }

            else -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Center
            ) {
                dataUser?.let { user ->
                    Text(
                        text = user.username.replaceFirstChar { it.uppercase() },
                        style = typography.h4
                    )
                    Spacer(modifier = Modifier.height(Dp16))
                    Button(
                        onClick = {
                            logout()
                            navController.navigateTo(
                                route = AuthRoute,
                                popUpTo = ProfileLandingRoute::class,
                                inclusive = true
                            )
                        }
                    ) {
                        Text(text = "Log Out")
                    }
                } ?: Text(
                    text = "No user data available",
                    style = typography.body1
                )
            }
        }
    }
}