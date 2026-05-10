package woowacourse.shopping.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.LastViewedProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class LastViewedProductRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val productRepository: ProductRepository,
) : LastViewedProductRepository {
    override suspend fun getLastViewedProduct(): Product? {
        val productId = dataStore.data.first()[LAST_VIEWED_PRODUCT_ID] ?: return null
        return productRepository.findProductById(Uuid.parse(productId))
    }

    override suspend fun saveLastViewedProduct(product: Product) {
        dataStore.edit { preferences ->
            preferences[LAST_VIEWED_PRODUCT_ID] = product.productId.toString()
        }
    }

    companion object {
        val LAST_VIEWED_PRODUCT_ID = stringPreferencesKey("last_viewed_product_id")
    }
}
