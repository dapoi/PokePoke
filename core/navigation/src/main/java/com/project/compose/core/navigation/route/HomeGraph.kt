package com.project.compose.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeGraph {
    @Serializable
    data object HomeLandingRoute : HomeGraph()

    @Serializable
    data class HomeDetailRoute(
        val pokemonId: Int
    ) : HomeGraph()
}