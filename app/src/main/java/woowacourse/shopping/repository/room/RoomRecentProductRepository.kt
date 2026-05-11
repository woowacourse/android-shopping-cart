package woowacourse.shopping.repository.room

import woowacourse.shopping.local.dao.RecentProductDao
import woowacourse.shopping.local.entity.RecentProductEntity
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import java.util.UUID

class RoomRecentProductRepository(
    private val recentProductDao: RecentProductDao,
    private val productRepository: ProductRepository,
) : RecentProductRepository {
    override suspend fun getRecentProducts(): Products {
        val productList =
            recentProductDao.getRecentItems().mapNotNull {
                productRepository.findProduct(id = it.productId)
            }
        return Products(productList)
    }

    override suspend fun getLastViewedProduct(): Product? {
        val productId = recentProductDao.getLastItem()?.productId ?: return null
        return productRepository.findProduct(productId)
    }

    override suspend fun add(productId: UUID) {
        recentProductDao.insert(RecentProductEntity(productId = productId, viewedAt = System.currentTimeMillis()))
        recentProductDao.deleteOldItems()
    }
}
