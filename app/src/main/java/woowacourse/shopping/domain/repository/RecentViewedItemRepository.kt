package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface RecentViewedItemRepository {
    fun loadAllRecentViewedItems(maxItemCount: Int): List<Product>

    fun saveRecentViewedItem(product: Product)

    fun getLastViewedProduct(): Product
}
