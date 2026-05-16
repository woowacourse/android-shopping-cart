package woowacourse.shopping.data.repository.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import woowacourse.shopping.data.local.dao.RecentProductDao
import woowacourse.shopping.data.local.entity.RecentProductEntity
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import java.util.UUID

class RoomRecentProductRepository(
    private val recentProductDao: RecentProductDao,
    private val productRepo: ProductRepository,
) : RecentProductRepository {
    override suspend fun getRecentProducts(): Products {
        val productList =
            recentProductDao.getRecentItems().mapNotNull {
                productRepo.findProduct(id = it.productId)
            }
        return Products(productList)
    }

    override suspend fun getLastViewedProduct(): Product? {
        val productId = recentProductDao.getLastItem()?.productId ?: return null
        return productRepo.findProduct(productId)
    }

    override suspend fun add(productId: UUID) {
        recentProductDao.insert(
            RecentProductEntity(
                productId = productId,
                viewedAt = System.currentTimeMillis()
            )
        )
        recentProductDao.deleteOldItems()
    }

    override fun observeRecent(): Flow<List<Product>> =
        recentProductDao.observeRecent().mapNotNull { entities ->
            entities.mapNotNull { entity ->
                productRepo.findProduct(entity.productId)
            }
        }

}
