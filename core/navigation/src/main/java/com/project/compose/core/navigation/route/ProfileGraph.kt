package com.project.compose.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class ProfileGraph {
    @Serializable
    data object ProfileLandingRoute : ProfileGraph()
}