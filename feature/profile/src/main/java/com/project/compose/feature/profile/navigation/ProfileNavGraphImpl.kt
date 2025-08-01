package com.project.compose.feature.profile.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.project.compose.core.navigation.base.BaseNavGraph
import com.project.compose.core.navigation.helper.composableScreen
import com.project.compose.core.navigation.route.InfoGraph.ProfileLandingRoute
import com.project.compose.feature.profile.screen.ProfileLandingScreen
import javax.inject.Inject

class ProfileNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<ProfileLandingRoute>(
            enterTransition = fadeIn(),
            popExitTransition = fadeOut()
        ) {
            ProfileLandingScreen(navController)
        }
    }
}