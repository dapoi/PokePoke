package com.project.compose.feature.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.project.compose.core.common.base.BaseViewModel
import com.project.compose.core.common.utils.state.UiState
import com.project.compose.core.common.utils.state.UiState.StateFailed
import com.project.compose.core.common.utils.state.UiState.StateInitial
import com.project.compose.core.common.utils.state.UiState.StateSuccess
import com.project.compose.core.data.repository.PokemonRepository
import com.project.compose.core.data.source.local.datastore.PokemonDataStore
import com.project.compose.core.data.source.local.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileLandingViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val dataStore: PokemonDataStore
) : BaseViewModel() {
    private val getProfileState = MutableStateFlow<UiState<UserEntity>>(StateInitial)
    val profileState = getProfileState.asStateFlow()

    fun getProfile() = viewModelScope.launch {
        repository.getUser().collect { user ->
            if (user != null) {
                getProfileState.value = StateSuccess(user)
            } else {
                getProfileState.value = StateFailed(Throwable("User not found"))
            }
        }
    }

    fun logout() = viewModelScope.launch {
        dataStore.clearToken()
        getProfileState.value = StateInitial
    }
}