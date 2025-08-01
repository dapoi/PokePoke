package com.project.compose.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.compose.core.data.source.local.model.PokemonEntity
import com.project.compose.core.data.source.local.model.RemoteKeyEntity
import com.project.compose.core.data.source.local.model.UserEntity
import com.project.compose.core.data.util.Converter

@Database(
    entities = [UserEntity::class, PokemonEntity::class, RemoteKeyEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}