package com.rehat.rehatcoffee.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rehat.rehatcoffee.core.Constants.MY_PREF
import com.rehat.rehatcoffee.core.Constants.USER_ROLE_KEY
import com.rehat.rehatcoffee.core.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MY_PREF)

@Singleton
class TokenDataStore @Inject constructor(val context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey(USER_TOKEN)
        private val USER_ROLE = stringPreferencesKey(USER_ROLE_KEY)
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun saveRoleLogin(role : String){
        dataStore.edit { preference ->
            preference[USER_ROLE] = role
        }
    }

    suspend fun clearUserToken() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
        }
    }

    val userTokenFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[USER_TOKEN_KEY] ?: ""
        }
    val userRole : Flow<String> = dataStore.data
        .map { preference ->
            preference[USER_ROLE] ?: ""
        }
}
