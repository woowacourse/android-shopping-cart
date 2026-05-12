package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.localdb.dao.RecentItemDao
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.data.localdb.mapper.toEntity
import woowacourse.shopping.model.Product

class RecentItemRepository(
    private val recentItemDao: RecentItemDao,
    private val productRepository: ProductRepository,
) {
    suspend fun addRecentItem(product: Product) {
        recentItemDao.insert(product.toEntity(System.currentTimeMillis()))
        recentItemDao.deleteOldItem()
    }

    fun getRecentItems(): Flow<List<Product>> =
        recentItemDao.getRecentItems().map { entities ->
            entities.mapNotNull { entity ->
                val product =
                    runCatching {
                        productRepository.getProductById(entity.id)
                    }.getOrNull()

                product?.let { entity.toDomain(it) }
            }
        }

    suspend fun getLastViewedItem(): Product? {
        val entity = recentItemDao.getLastViewedItem() ?: return null
        val product =
            runCatching {
                productRepository.getProductById(entity.id)
            }.getOrNull()

        return product?.let { entity.toDomain(it) }
    }
}
