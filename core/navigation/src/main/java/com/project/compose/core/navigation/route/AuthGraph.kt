package com.project.compose.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthGraph {
    @Serializable
    data object AuthRoute : AuthGraph()
}