package com.project.compose.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.project.compose.core.navigation.base.BaseNavGraph
import com.project.compose.core.navigation.helper.composableScreen
import com.project.compose.core.navigation.route.SplashGraph.SplashRoute
import com.project.compose.feature.splash.screen.SplashScreen
import javax.inject.Inject

class SplashNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<SplashRoute> { SplashScreen(navController) }
    }
}