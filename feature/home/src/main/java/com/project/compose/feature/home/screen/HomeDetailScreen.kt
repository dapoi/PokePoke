package com.project.compose.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.compose.core.common.R
import com.project.compose.core.common.base.BaseScreen
import com.project.compose.core.common.ui.component.PokeImage
import com.project.compose.core.common.ui.component.attr.AppTopBarAttr.TopBarArgs
import com.project.compose.core.common.ui.theme.Dimens.Dp16
import com.project.compose.core.common.ui.theme.Dimens.Dp210
import com.project.compose.core.common.ui.theme.Dimens.Dp24
import com.project.compose.core.common.ui.theme.PokeTheme
import com.project.compose.core.common.utils.state.collectAsStateValue
import com.project.compose.core.data.source.local.model.PokemonEntity
import com.project.compose.feature.home.viewmodel.HomeDetailViewModel

@Composable
fun HomeDetailScreen(
    navController: NavController,
    viewModel: HomeDetailViewModel = hiltViewModel()
) = with(viewModel) {
    val pokemonDetailState = pokemonDetailState.collectAsStateValue()
    var showLoading by remember { mutableStateOf(false) }
    var pokemon by remember { mutableStateOf<PokemonEntity?>(null) }
    var showError by remember { mutableStateOf(false) }

    BaseScreen(
        topBarArgs = TopBarArgs(
            title = { Text("Pokémon Detail") },
            iconBack = R.drawable.ic_back,
            onClickBack = { navController.popBackStack() }
        )
    ) {
        LaunchedEffect(pokemonDetailState) {
            pokemonDetailState.handleUiState(
                onLoading = { showLoading = true },
                onSuccess = {
                    showLoading = false
                    pokemon = it
                },
                onFailed = {
                    showLoading = false
                    showError = true
                }
            )
        }

        when {
            showLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator()
            }

            showError -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Failed to load Pokémon details. Please try again.")
                Button(onClick = { getPokemonDetail() }) {
                    Text(text = "Retry")
                }
            }

            else -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = pokemon?.name.orEmpty().replaceFirstChar { it.uppercase() },
                            style = PokeTheme.typography.h2
                        )
                        Spacer(modifier = Modifier.height(Dp16))
                        PokeImage(
                            modifier = Modifier.size(Dp210),
                            id = pokemon?.id ?: 0
                        )
                        Spacer(modifier = Modifier.height(Dp24))
                        Text(
                            text = "Abilities:",
                            style = PokeTheme.typography.h3
                        )
                        Spacer(modifier = Modifier.height(Dp16))
                        pokemon?.abilities?.forEach { ability ->
                            Text(
                                text = ability,
                                style = PokeTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}