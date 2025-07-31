package com.project.compose.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.compose.core.common.base.BaseScreen
import com.project.compose.core.common.ui.component.attr.AppTopBarAttr.TopBarArgs
import com.project.compose.feature.home.viewmodel.HomeLandingViewModel

@Composable
internal fun HomeLandingScreen(
    navController: NavController,
    viewModel: HomeLandingViewModel = hiltViewModel()
) = with(viewModel) {
    BaseScreen(
        topBarArgs = TopBarArgs(title = "Pokemon Catalogue"),
    ) { }
}