package com.project.compose.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.project.compose.core.common.base.BaseViewModel
import com.project.compose.core.common.utils.state.UiState
import com.project.compose.core.common.utils.state.UiState.StateInitial
import com.project.compose.core.data.repository.PokemonRepository
import com.project.compose.core.data.source.local.model.PokemonEntity
import com.project.compose.core.navigation.route.HomeGraph.HomeDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val args by lazy { savedStateHandle.toRoute<HomeDetailRoute>() }

    private val _pokemonDetailState = MutableStateFlow<UiState<PokemonEntity>>(StateInitial)
    val pokemonDetailState = _pokemonDetailState.asStateFlow()

    init {
        getPokemonDetail()
    }

    fun getPokemonDetail() = collectApiAsUiState(
        response = repository.getPokemonDetail(args.pokemonId),
        updateState = { _pokemonDetailState.value = it }
    )
}