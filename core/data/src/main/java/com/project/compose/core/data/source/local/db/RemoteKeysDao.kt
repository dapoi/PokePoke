package com.project.compose.core.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.project.compose.core.data.source.local.model.RemoteKeyEntity

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertKeys(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE pokemonId = :pokemonId")
    suspend fun remoteKeysPokemonId(pokemonId: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT createdAt FROM remote_keys ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}