package com.scoto.fodamy.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreManager @Inject constructor(
    private val context: Context
) {

    val isFirstTimeLaunch: Flow<String> = context.dataStore.data.map { preference ->
        preference[FIRST_LAUNCH] ?: ""
    }

    val token: Flow<String> = context.dataStore.data.map { preference ->
        preference[AUTH_TOKEN] ?: ""
    }

    suspend fun saveFirstTimeLaunched(value: String = "launched") {
        context.dataStore.edit { preference ->
            preference[FIRST_LAUNCH] = value
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preference ->
            preference[AUTH_TOKEN] = token
        }
    }

    suspend fun getToken(): String {
        //first operator to get a single value and stop collection from the flow.
        return context.dataStore.data.map { it[AUTH_TOKEN] ?: "" }.first()
    }

    suspend fun removeAuth() {
        context.dataStore.edit {
            it.remove(AUTH_TOKEN)
        }
    }

    companion object {

        private const val FODAMY_LAUNCH_PREF = "FODAMY_LAUNCH_PREF"
        private val FIRST_LAUNCH = stringPreferencesKey("first_launched")
        private val AUTH_TOKEN = stringPreferencesKey("AUTH_TOKEN")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FODAMY_LAUNCH_PREF)


    }
}