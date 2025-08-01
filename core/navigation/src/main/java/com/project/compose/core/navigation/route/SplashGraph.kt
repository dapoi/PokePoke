package com.project.compose.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class SplashGraph {
    @Serializable
    data object SplashRoute : SplashGraph()
}