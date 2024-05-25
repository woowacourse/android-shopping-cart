package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.recentviewedItem.RecentViewedItemDatabase
import woowacourse.shopping.data.model.RecentViewedItemEntity.Companion.makeRecentViewedItemEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
import kotlin.concurrent.thread

class RecentViewedItemRepositoryImpl(context: Context) : RecentViewedItemRepository {
    private val recentViewedItemDao = RecentViewedItemDatabase.getInstance(context).recentViewedItemDao()

    override fun loadAllRecentViewedItems(): List<Product> {
        val recentItems = mutableListOf<Product>()
        thread {
            val products = recentViewedItemDao.findAllItemsByMostRecent().map { it.product }
            recentItems.addAll(products)
        }.join()
        return recentItems
    }

    override fun addRecentViewedItem(product: Product) {
        val newEntity = makeRecentViewedItemEntity(product)
        thread { recentViewedItemDao.saveRecentViewedItem(newEntity) }.join()
    }
}
