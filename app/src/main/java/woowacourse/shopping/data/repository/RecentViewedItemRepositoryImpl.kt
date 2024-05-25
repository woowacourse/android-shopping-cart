package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.recentviewedItem.RecentViewedItemDatabase
import woowacourse.shopping.data.model.RecentViewedItemEntity.Companion.makeRecentViewedItemEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
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
        val newEntity = makeRecentViewedItemEntity(product)
        thread { recentViewedItemDao.saveRecentViewedItem(newEntity) }.join()
    }

    override fun getLastViewedProduct(): Product {
        var lastViewed: Product? = null
        thread { lastViewed = recentViewedItemDao.findItemByMostRecent()?.product }.join()
        return lastViewed ?: Product.INVALID_PRODUCT
    }
}
