package woowacourse.shopping.data.recentvieweditem

import woowacourse.shopping.data.recentvieweditem.RecentViewedItemEntity.Companion.makeRecentViewedItemEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
import java.time.LocalDateTime

class RecentViewedItemRepositoryImpl(private val recentViewedLocalDataSource: RecentViewedLocalDataSource) :
    RecentViewedItemRepository {
    override fun loadAllRecentViewedItems(maxItemCount: Int): List<Product> {
        val recentItems = mutableListOf<Product>()
        val products = recentViewedLocalDataSource.loadAll().map { it.product }

        recentItems.addAll(products)
        return recentItems.take(maxItemCount)
    }

    override fun saveRecentViewedItem(product: Product) {
        val existingItem = recentViewedLocalDataSource.findByProductId(product.id)
        if (existingItem == null) {
            val newEntity = makeRecentViewedItemEntity(product)
            recentViewedLocalDataSource.save(newEntity)
        } else {
            recentViewedLocalDataSource.update(product.id, LocalDateTime.now())
        }
    }

    override fun getLastViewedProduct(): Product {
        val mostRecentItem = recentViewedLocalDataSource.findMostRecent()?.product
        return mostRecentItem ?: Product.INVALID_PRODUCT
    }
}
