package woowacourse.shopping.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(
    name = "shopping_preferences",
)
