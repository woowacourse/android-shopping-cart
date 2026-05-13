package woowacourse.shopping.data.local.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.domain.Product

interface RecentlyViewedProductRepository {
    fun getAll(): Flow<List<RecentlyViewedProductEntity>?>

    suspend fun updateList(product: Product)

    fun getLatestItem(): Flow<String?>
}