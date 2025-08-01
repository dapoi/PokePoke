package com.project.compose.core.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("pokemon_datastore")

class PokemonDataStore @Inject constructor(context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveToken(token: Int) {
        dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    val getToken = dataStore.data.map { preferences ->
        preferences[KEY_TOKEN] ?: 0
    }

    companion object {
        val KEY_TOKEN = intPreferencesKey("token")
    }
}