package woowacourse.shopping.data.recent

import woowacourse.shopping.data.recent.entity.RecentProduct
import java.time.LocalDateTime

class FakeRecentProductRepository : RecentProductRepository {
    private val recentProducts: MutableMap<Long, RecentProduct> = mutableMapOf()
    private var id: Long = 0L

    override fun findLastOrNull(): RecentProduct? {
        if (recentProducts.isEmpty()) return null
        return recentProducts.entries.last().value
    }

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
        recentProducts[productId] = RecentProduct(id++, productId, LocalDateTime.now())
    }

    companion object {
        private const val FIND_RECENT_PRODUCTS_COUNT = 10
    }
}
