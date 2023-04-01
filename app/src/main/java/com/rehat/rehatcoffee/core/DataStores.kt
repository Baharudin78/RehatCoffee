import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_preferences")

class UserTokenStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    val userTokenFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[USER_TOKEN_KEY] ?: ""
        }
}
