package woowacourse.shopping.data.recent

import woowacourse.shopping.model.RecentProduct
import java.time.LocalDateTime

object DummyRecentProductRepository : RecentProductRepository {
    private val recentProducts: MutableMap<Long, RecentProduct> = mutableMapOf()

    private const val FIND_RECENT_PRODUCTS_COUNT = 10

    override fun findRecentProducts(): List<RecentProduct> {
        return recentProducts.asSequence()
            .map { it.value }
            .take(FIND_RECENT_PRODUCTS_COUNT)
            .toList()
            .reversed()
    }

    override fun save(productId: Long) {
        if (recentProducts.contains(productId)) {
            recentProducts.remove(productId)
        }
        recentProducts[productId] = RecentProduct(productId, LocalDateTime.now())
    }
}
