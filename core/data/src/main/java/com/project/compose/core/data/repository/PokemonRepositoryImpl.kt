package com.project.compose.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.project.compose.core.data.repository.base.BaseRepository
import com.project.compose.core.data.source.local.db.PokemonDatabase
import com.project.compose.core.data.source.local.model.PokemonEntity
import com.project.compose.core.data.source.paging.PokemonsRemoteMediator
import com.project.compose.core.data.source.remote.service.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: PokemonDatabase
) : PokemonRepository, BaseRepository() {
    override fun getPokemons() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = PokemonsRemoteMediator(api, db),
        pagingSourceFactory = { db.pokemonDao().getPokemons() }
    ).flow

    override fun getPokemonBySearch(query: String): Flow<PagingData<PokemonEntity>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { db.pokemonDao().pagingSourceSearch(dbQuery) }
        ).flow
    }
}