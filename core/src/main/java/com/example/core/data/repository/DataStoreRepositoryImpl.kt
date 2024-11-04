package com.example.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = DataStoreRepositoryImpl.PREFERENCES_NAME)

internal class DataStoreRepositoryImpl(private val context: Context) : DataStoreRepository {


    override suspend fun <T> save(key: String, value: T) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
            }
        }
    }

    override fun getString(key: String): Flow<String?> =
        context.dataStore.data.map { it[stringPreferencesKey(key)] }

    override suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }


    companion object {
        const val PREFERENCES_NAME = "chat_app_preferences"
    }
}