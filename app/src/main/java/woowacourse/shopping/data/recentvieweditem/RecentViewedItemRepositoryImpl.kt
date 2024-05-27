package woowacourse.shopping.data.recentvieweditem

import android.content.Context
import woowacourse.shopping.data.recentvieweditem.RecentViewedItemEntity.Companion.makeRecentViewedItemEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
import java.time.LocalDateTime
import kotlin.concurrent.thread

class RecentViewedItemRepositoryImpl(context: Context) : RecentViewedItemRepository {
    private val recentViewedItemDao = RecentViewedItemDatabase.getInstance(context).recentViewedItemDao()

    override fun loadAllRecentViewedItems(maxItemCount: Int): List<Product> {
        val recentItems = mutableListOf<Product>()
        thread {
            val products = recentViewedItemDao.findAllItemsByMostRecent().map { it.product }
            recentItems.addAll(products)
        }.join()
        return recentItems.take(maxItemCount)
    }

    override fun saveRecentViewedItem(product: Product) {
        thread {
            val existingItem = recentViewedItemDao.findItemByProductId(product.id)
            if (existingItem == null) {
                val newEntity = makeRecentViewedItemEntity(product)
                recentViewedItemDao.saveRecentViewedItem(newEntity)
            } else {
                recentViewedItemDao.updateViewedDate(product.id, LocalDateTime.now())
            }
        }.join()
    }

    override fun getLastViewedProduct(): Product {
        var lastViewed: Product? = null
        thread { lastViewed = recentViewedItemDao.findItemByMostRecent()?.product }.join()
        return lastViewed ?: Product.INVALID_PRODUCT
    }
}
