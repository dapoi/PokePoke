package com.project.compose.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.project.compose.core.navigation.base.BaseNavGraph
import com.project.compose.core.navigation.helper.composableScreen
import com.project.compose.core.navigation.route.AuthGraph.AuthRoute
import com.project.compose.feature.auth.screen.AuthScreen
import javax.inject.Inject

class AuthNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<AuthRoute> { AuthScreen(navController) }
    }
}