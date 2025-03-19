package com.hskflashcard.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val IS_SIMPLIFIED_KEY = booleanPreferencesKey("is_simplified_key")
    }

    suspend fun saveIsSimplified(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SIMPLIFIED_KEY] = value
        }
    }

    suspend fun getIsSimplified(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[IS_SIMPLIFIED_KEY] ?: true
        }.first()
    }

}