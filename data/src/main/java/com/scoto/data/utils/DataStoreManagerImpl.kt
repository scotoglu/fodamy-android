package com.scoto.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.scoto.domain.utils.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 09:52
 */

@Singleton
class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : DataStoreManager {

    override val token: Flow<String>
        get() = context.dataStore.data.map { pref -> pref[AUTH_TOKEN] ?: "" }

    override suspend fun isLogin(): Boolean = getToken().isNotBlank()

    override suspend fun isUserComment(commentUserId: Int): Boolean = getUserId() == commentUserId

    override suspend fun isLoginLiveData(): LiveData<String> = token.asLiveData()

    override suspend fun isFirstTimeLaunch(): Boolean {
        return context.dataStore.data.map { it[FIRST_LAUNCH] ?: true }.first()
    }


    override suspend fun saveFirstTimeLaunched() {
        context.dataStore.edit { it[FIRST_LAUNCH] = false }
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { pref -> pref[AUTH_TOKEN] = token }
    }

    override suspend fun getToken(): String =
        context.dataStore.data.map { pref -> pref[AUTH_TOKEN] ?: "" }.first()

    override suspend fun removeToken() {
        context.dataStore.edit {
            it.remove(AUTH_TOKEN)
        }
    }

    override suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { pref ->
            pref[USER_ID] = userId
        }
    }

    override suspend fun getUserId(): Int =
        context.dataStore.data.map { pref -> pref[USER_ID] ?: 0 }.first()

    override suspend fun removeUserId() {
        context.dataStore.edit { pref ->
            pref.remove(USER_ID)
        }
    }

    companion object {

        private const val FODAMY_LAUNCH_PREF = "FODAMY_LAUNCH_PREF"
        private val FIRST_LAUNCH = booleanPreferencesKey("IS_FIRST_LAUNCHED")
        private val AUTH_TOKEN = stringPreferencesKey("AUTH_TOKEN")
        private val USER_ID = intPreferencesKey("USER_ID")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FODAMY_LAUNCH_PREF)
    }
}