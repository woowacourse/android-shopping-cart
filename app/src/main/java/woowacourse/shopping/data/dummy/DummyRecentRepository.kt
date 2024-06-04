package woowacourse.shopping.data.dummy

import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.repository.RecentRepository

object DummyRecentRepository : RecentRepository {
    private val recentProducts: MutableList<RecentProductItem> = mutableListOf()

    override fun loadAll(): Result<List<RecentProductItem>> =
        runCatching {
            recentProducts.toList()
        }

    override fun loadMostRecent(): Result<RecentProductItem?> =
        runCatching {
            recentProducts.lastOrNull()
        }

    override fun add(recentProduct: RecentProductItem): Result<Long> =
        runCatching {
            removeExistingRecentProduct(recentProduct)
            recentProducts.add(recentProduct)
            recentProduct.productId
        }

    private fun removeExistingRecentProduct(recentProduct: RecentProductItem) {
        val existingIndex = recentProducts.indexOfFirst { it.productId == recentProduct.productId }
        if (existingIndex != -1) {
            recentProducts.removeAt(existingIndex)
        }
    }
}
