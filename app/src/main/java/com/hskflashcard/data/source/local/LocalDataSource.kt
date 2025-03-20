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
        private val HAS_BEEN_POPULATED = booleanPreferencesKey("has_been_populated_key")
    }

    suspend fun saveHasBeenPopulated(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_BEEN_POPULATED] = value
        }
    }

    suspend fun getHasBeenPopulated(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[HAS_BEEN_POPULATED] == true
        }.first()
    }

}