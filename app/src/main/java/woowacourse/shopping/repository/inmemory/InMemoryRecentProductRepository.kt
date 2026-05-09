package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.RecentProductRepository

object InMemoryRecentProductRepository : RecentProductRepository {
    private val recentProducts = mutableListOf<RecentProduct>()

    fun clear() {
        recentProducts.clear()
    }

    override suspend fun recordView(productId: ProductId) {
        recentProducts.removeAll { it.productId == productId }
        recentProducts.add(
            RecentProduct(
                productId = productId,
                viewedAtMillis = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun getRecentProducts(limit: Int): List<RecentProduct> {
        val safeLimit = limit.coerceAtLeast(0)

        return recentProducts
            .asReversed()
            .take(safeLimit)
    }

    override suspend fun getLatestViewedProductExcluding(productId: ProductId): RecentProduct? =
        recentProducts
            .asReversed()
            .firstOrNull { it.productId != productId }
}
