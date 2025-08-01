package com.project.compose.core.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val pokemonId: String,
    val createdAt: Long,
    val nextKey: Int?,
    val prevKey: Int?
)