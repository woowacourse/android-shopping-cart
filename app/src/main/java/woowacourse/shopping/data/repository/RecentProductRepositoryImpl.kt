package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.RecentProductDao
import woowacourse.shopping.data.local.RecentProductEntity
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class
RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao,
    private val productRepository: ProductRepository,
) : RecentProductRepository {
    override fun getRecentProducts(): Flow<List<Product>> {
        return recentProductDao.getRecentProducts().map { entities ->
            val result = mutableListOf<Product>()
            for (entity in entities) {
                val product = productRepository.getProduct(entity.id)
                if (product != null) {
                    result.add(product)
                }
            }
            result
        }
    }

    override suspend fun saveRecentProduct(product: Product) {
        recentProductDao.insertWithLimit(
            RecentProductEntity(
                id = product.id,
                imageUrl = product.imageUrl,
                name = product.productTitle.value,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}
