package woowacourse.shopping.repository

import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.RecentProduct

class FakeRecentProductRepository : RecentProductRepository {
    companion object {
        private const val MAX_RECENT_PRODUCTS = 10
    }

    private val recentProducts = mutableListOf<RecentProduct>()

    override suspend fun recordView(productId: ProductId) {
        recentProducts.removeAll { it.productId == productId }
        recentProducts.add(
            RecentProduct(
                productId = productId,
                viewedAtMillis = System.currentTimeMillis(),
            ),
        )
        trimToMax()
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

    private fun trimToMax() {
        val overflow = recentProducts.size - MAX_RECENT_PRODUCTS
        if (overflow > 0) {
            repeat(overflow) {
                recentProducts.removeAt(0)
            }
        }
    }
}
