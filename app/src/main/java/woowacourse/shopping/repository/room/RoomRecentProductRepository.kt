package woowacourse.shopping.repository.room

import woowacourse.shopping.local.dao.RecentProductDao
import woowacourse.shopping.local.entity.RecentProductEntity
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import java.util.UUID

class RoomRecentProductRepository(
    private val recentProductDao: RecentProductDao,
    private val productRepository: ProductRepository
): RecentProductRepository {
    override suspend fun getRecentProducts(): Products {
        val productList = this@RoomRecentProductRepository.recentProductDao.getRecentItems().mapNotNull {
            productRepository.findProduct(id = it.productId)
        }
        return Products(productList)
    }

    override suspend fun add(productId: UUID) {
        this@RoomRecentProductRepository.recentProductDao.insert(RecentProductEntity(productId = productId, viewedAt = System.currentTimeMillis()))
        this@RoomRecentProductRepository.recentProductDao.deleteOldItems()
    }
}