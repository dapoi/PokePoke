package com.project.compose.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.compose.core.data.source.local.model.PokemonEntity
import com.project.compose.core.data.source.local.model.RemoteKeyEntity

@Database(
    entities = [PokemonEntity::class, RemoteKeyEntity::class],
    exportSchema = false,
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}