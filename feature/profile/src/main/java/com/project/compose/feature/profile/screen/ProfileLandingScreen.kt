package com.project.compose.feature.profile.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.compose.feature.profile.viewmodel.ProfileLandingViewModel

@Composable
internal fun ProfileLandingScreen(
    navController: NavController,
    viewModel: ProfileLandingViewModel = hiltViewModel()
) = with(viewModel) {

}