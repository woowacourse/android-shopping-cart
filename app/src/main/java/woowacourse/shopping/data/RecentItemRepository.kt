package woowacourse.shopping.data

import woowacourse.shopping.data.localdb.dao.RecentItemDao
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.data.localdb.mapper.toEntity
import woowacourse.shopping.model.Product

class RecentItemRepository(
    private val recentItemDao: RecentItemDao,
) {
    suspend fun addRecentItem(product: Product) {
        recentItemDao.insert(product.toEntity(System.currentTimeMillis()))
        recentItemDao.deleteOldItem()
    }

    suspend fun getRecentItems(): List<Product> = recentItemDao.getRecentItems().map { it.toDomain() }

    suspend fun getLastViewedItem(): Product? = recentItemDao.getLastViewedItem()?.toDomain()
}
