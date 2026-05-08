package woowacourse.shopping.repository.recentviewedproduct

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface RecentlyViewedProductsRepository {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun saveViewedProduct(productId: Uuid)

    fun getRecentlyViewedProducts(): Flow<Products>

    suspend fun getLastViewedProduct(): Product?
}