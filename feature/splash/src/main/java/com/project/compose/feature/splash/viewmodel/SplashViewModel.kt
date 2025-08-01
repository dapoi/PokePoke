package com.project.compose.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.project.compose.core.common.base.BaseViewModel
import com.project.compose.core.data.source.local.datastore.PokemonDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    dataStore: PokemonDataStore
) : BaseViewModel() {
    val token = dataStore.getToken.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = 0
    )
}