package com.project.compose.core.data.repository

import androidx.paging.PagingData
import com.project.compose.core.common.utils.state.ApiState
import com.project.compose.core.data.source.local.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<PagingData<PokemonEntity>>
    fun getPokemonBySearch(query: String): Flow<PagingData<PokemonEntity>>
    fun getPokemonDetail(id: Int): Flow<ApiState<PokemonEntity>>
}