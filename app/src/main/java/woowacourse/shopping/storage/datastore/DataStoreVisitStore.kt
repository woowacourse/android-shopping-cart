package woowacourse.shopping.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val Context.visitDataStore: DataStore<Preferences> by preferencesDataStore(name = "visit_datastore")

class DataStoreVisitStore(
    private val context: Context,
    private val scope: CoroutineScope,
) : VisitStore {
    override val recentVisitedProductIds: StateFlow<List<Long>> =
        context.visitDataStore.data
            .map { preferences -> readRecentVisitedProductIds(preferences) }
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    override suspend fun visit(productId: Long) {
        updateRecentVisitedProductIds { currentProductIds ->
            (listOf(productId) + currentProductIds.filterNot { id -> id == productId })
        }
    }

    private suspend fun updateRecentVisitedProductIds(transform: (List<Long>) -> List<Long>) {
        context.visitDataStore.edit { preferences ->
            val currentProductIds = readRecentVisitedProductIds(preferences)
            val updatedProductIds = transform(currentProductIds).distinct().take(MAX_RECENT_VISITS)
            if (updatedProductIds.isEmpty()) {
                clearRecentVisitedProductIds(preferences)
                return@edit
            }
            writeRecentVisitedProductIds(
                preferences = preferences,
                productIds = updatedProductIds,
            )
        }
    }

    private fun readRecentVisitedProductIds(preferences: Preferences): List<Long> {
        val currentProductIds =
            recentVisitedProductIdKeys
                .mapNotNull { key -> preferences[key] }
                .distinct()
                .take(MAX_RECENT_VISITS)
        if (currentProductIds.isNotEmpty()) {
            return currentProductIds
        }
        return parseLegacyProductIds(preferences[legacyRecentVisitedProductIdsKey])
    }

    private fun writeRecentVisitedProductIds(
        preferences: MutablePreferences,
        productIds: List<Long>,
    ) {
        clearRecentVisitedProductIds(preferences)
        productIds.forEachIndexed { index, productId ->
            preferences[recentVisitedProductIdKeys[index]] = productId
        }
    }

    private fun clearRecentVisitedProductIds(preferences: MutablePreferences) {
        recentVisitedProductIdKeys.forEach { key -> preferences.remove(key) }
        preferences.remove(legacyRecentVisitedProductIdsKey)
    }

    private fun parseLegacyProductIds(raw: String?): List<Long> =
        raw
            ?.split(LEGACY_ID_SEPARATOR)
            ?.mapNotNull { token -> token.toLongOrNull() }
            ?.distinct()
            ?.take(MAX_RECENT_VISITS)
            ?: emptyList()

    private companion object {
        const val MAX_RECENT_VISITS = 10
        const val LEGACY_ID_SEPARATOR = ","
        val legacyRecentVisitedProductIdsKey = stringPreferencesKey("recent_visited_product_ids")
        val recentVisitedProductIdKeys =
            List(MAX_RECENT_VISITS) { index ->
                longPreferencesKey("recent_visited_product_id_$index")
            }
    }
}
