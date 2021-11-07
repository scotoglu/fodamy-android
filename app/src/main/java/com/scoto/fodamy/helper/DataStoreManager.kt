package com.scoto.fodamy.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreManager @Inject constructor(
    val context: Context
) {

    val isFirstTimeLaunch: Flow<String> = context.dataStore.data.map { preference ->
        preference[FIRST_LAUNCH] ?: ""
    }

    suspend fun saveFirstTimeLaunched(value: String = "launched") {
        context.dataStore.edit { preference ->
            preference[FIRST_LAUNCH] = value
        }
    }

    companion object {

        private const val FODAMY_LAUNCH_PREF = "FODAMY_LAUNCH_PREF"
        private val FIRST_LAUNCH = stringPreferencesKey("first_launched")
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FODAMY_LAUNCH_PREF)


    }
}