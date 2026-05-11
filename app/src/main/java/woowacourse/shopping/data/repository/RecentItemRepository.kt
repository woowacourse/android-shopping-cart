package woowacourse.shopping.data.repository

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

    suspend fun getRecentItems(): List<Product> =
        recentItemDao.getRecentItems().mapNotNull { entity ->
            val product =
                runCatching {
                    productRepository.getProductById(entity.id)
                }.getOrNull()

            product?.let { toDomain(it) }
        }

    suspend fun getLastViewedItem(): Product? {
        val entity = recentItemDao.getLastViewedItem() ?: return null
        val product =
            runCatching {
                productRepository.getProductById(entity.id)
            }.getOrNull()

        return product?.let { toDomain(it) }
    }
}
