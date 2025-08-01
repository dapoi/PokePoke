package com.project.compose.feature.splash.screen

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.compose.core.common.base.BaseScreen
import com.project.compose.core.common.ui.theme.PokeTheme.typography
import com.project.compose.core.common.utils.state.collectAsStateValue
import com.project.compose.core.navigation.helper.navigateTo
import com.project.compose.core.navigation.route.AuthGraph.AuthRoute
import com.project.compose.core.navigation.route.HomeGraph.HomeLandingRoute
import com.project.compose.core.navigation.route.SplashGraph.SplashRoute
import com.project.compose.feature.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
internal fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) = with(viewModel) {
    val token = token.collectAsStateValue()
    val notifPermission = rememberLauncherForActivityResult(RequestPermission()) {}
    BaseScreen(clipToTopBar = true) {
        LaunchedEffect(Unit) {
            if (SDK_INT >= TIRAMISU) notifPermission.launch(POST_NOTIFICATIONS)
        }

        LaunchedEffect(token) {
            delay(1000)
            navController.navigateTo(
                route = if (token != 0) HomeLandingRoute else AuthRoute,
                popUpTo = SplashRoute::class,
                inclusive = true
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            Text(
                text = "Pokémon",
                style = typography.h1,
                color = colorScheme.primary
            )
        }
    }
}