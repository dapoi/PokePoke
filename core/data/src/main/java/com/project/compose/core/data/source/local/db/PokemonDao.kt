package com.project.compose.core.data.source.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.project.compose.core.data.source.local.model.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemons()

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE :query ORDER BY id ASC")
    fun pagingSourceSearch(query: String): PagingSource<Int, PokemonEntity>
}