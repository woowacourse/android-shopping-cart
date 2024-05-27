package woowacourse.shopping

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentViewedItemRepository

class FakeRecentItemRepository(inputs: List<Product> = emptyList()) : RecentViewedItemRepository {
    private val recentViewedItems = inputs.toMutableList()

    override fun loadAllRecentViewedItems(maxItemCount: Int): List<Product> {
        return recentViewedItems.reversed().take(maxItemCount)
    }

    override fun saveRecentViewedItem(product: Product) {
        val index = recentViewedItems.indexOfFirst { it.id == product.id }
        if (index != -1) {
            recentViewedItems.remove(product)
        }
        recentViewedItems.add(product)
    }

    override fun getLastViewedProduct(): Product {
        return recentViewedItems.last()
    }
}
