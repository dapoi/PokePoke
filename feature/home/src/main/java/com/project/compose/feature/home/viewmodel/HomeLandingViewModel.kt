package com.project.compose.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.project.compose.core.common.base.BaseViewModel
import com.project.compose.core.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeLandingViewModel @Inject constructor(
    repository: PokemonRepository
) : BaseViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive = _isSearchActive.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearchQuery() {
        _isSearchActive.value = _isSearchActive.value.not()
        if (_isSearchActive.value.not()) onSearchQueryChanged("")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemons = _searchQuery.flatMapLatest { query ->
        if (query.isBlank()) {
            repository.getPokemons().cachedIn(viewModelScope)
        } else {
            repository.getPokemonBySearch(query).cachedIn(viewModelScope)
        }
    }
}