package com.project.compose.core.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<PokemonResponse>? = null,
) {
    data class PokemonResponse(
        @SerializedName("name") val name: String? = null,
        @SerializedName("url") val url: String? = null
    ) {
        val id: Int
            get() = url?.substringAfterLast("/")?.toIntOrNull() ?: 0
    }
}