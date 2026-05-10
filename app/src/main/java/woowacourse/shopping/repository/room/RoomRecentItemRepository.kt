package woowacourse.shopping.repository.room

import woowacourse.shopping.local.dao.RecentItemDao
import woowacourse.shopping.local.entity.RecentItemEntity
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentItemRepository
import java.util.UUID

class RoomRecentItemRepository(
    private val recentItemDao: RecentItemDao,
    private val productRepository: ProductRepository
): RecentItemRepository {
    override suspend fun getRecentItems(): Products {
        val productList = recentItemDao.getRecentItems().mapNotNull {
            productRepository.findProduct(id = it.productId)
        }
        return Products(productList)
    }

    override suspend fun add(productId: UUID) {
        recentItemDao.insert(RecentItemEntity(productId = productId, viewedAt = System.currentTimeMillis()))
        recentItemDao.deleteOldItems()
    }
}