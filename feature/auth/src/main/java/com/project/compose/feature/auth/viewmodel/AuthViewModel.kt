package com.project.compose.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.project.compose.core.common.base.BaseViewModel
import com.project.compose.core.common.utils.state.UiState
import com.project.compose.core.common.utils.state.UiState.StateFailed
import com.project.compose.core.common.utils.state.UiState.StateInitial
import com.project.compose.core.common.utils.state.UiState.StateLoading
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
class AuthViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val dataStore: PokemonDataStore
) : BaseViewModel() {

    private val _authState = MutableStateFlow<UiState<UserEntity>>(StateInitial)
    val authState = _authState.asStateFlow()

    fun register(userEntity: UserEntity) = viewModelScope.launch {
        repository.register(userEntity)
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _authState.value = StateLoading
        val user = repository.login(username, password)
        user?.let {
            dataStore.saveToken(it.token)
            _authState.value = StateSuccess(it)
        } ?: run {
            _authState.value = StateFailed(Throwable("Invalid username or password"))
        }
    }

    fun resetAuthState() {
        _authState.value = StateInitial
    }
}