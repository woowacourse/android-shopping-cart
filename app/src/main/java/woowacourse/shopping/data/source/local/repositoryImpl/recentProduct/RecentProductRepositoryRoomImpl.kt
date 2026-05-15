package woowacourse.shopping.data.source.local.repositoryImpl.recentProduct

import woowacourse.shopping.data.source.local.dao.RecentProductDao
import woowacourse.shopping.domain.RecentProductRepository
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.repository.ProductRepository

class RecentProductRepositoryRoomImpl(
    private val recentProductDao: RecentProductDao,
    private val productRepository: ProductRepository,
) : RecentProductRepository {
    override suspend fun addRecentProduct(productId: String) {
        recentProductDao.addRecentProduct(productId)
    }

    override suspend fun getAllRecentProducts(): List<Product> {
        val recentEntity = recentProductDao.getAllRecentProducts()

        return recentEntity.mapNotNull { entity ->
            try {
                productRepository.getProduct(entity.productId)
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getMostRecentProduct(): Product? {
        val recentEntity = recentProductDao.getMostRecentProduct()
        return recentEntity?.let { entity ->
            try {
                productRepository.getProduct(entity.productId)
            } catch (e: Exception) {
                null
            }
        }
    }
}
