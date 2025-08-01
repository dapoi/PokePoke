package com.project.compose.core.data.source.remote.service

import com.project.compose.core.data.source.remote.model.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int
    ): PokemonsResponse
}