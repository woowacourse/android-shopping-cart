package woowacourse.shopping.data.repository

import woowacourse.shopping.data.database.AppDatabase
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.mapper.toEntityModel
import woowacourse.shopping.data.model.RecentlyViewedProductEntity
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository

class InMemoryRecentlyViewedProductsRepository private constructor(database: AppDatabase) : RecentlyViewedProductsRepository {
    private val recentlyProductDao = database.recentlyProductDao()

    override fun insertRecentlyViewedProduct(product: RecentlyViewedProduct) {
        threadAction {
            val existingProduct = recentlyProductDao.findRecentlyViewedProductById(product.productId)
            if (existingProduct != null) {
                val updatedProduct = existingProduct.copy(viewedAt = System.currentTimeMillis())
                recentlyProductDao.insertRecentlyViewedProduct(updatedProduct)
            } else {
                recentlyProductDao.insertRecentlyViewedProduct(product.toEntityModel())
            }
        }
    }

    override fun getRecentlyViewedProducts(limit: Int): Result<List<RecentlyViewedProduct>> {
        return runCatching {
            var products = emptyList<RecentlyViewedProductEntity>()
            threadAction {
                products = recentlyProductDao.getRecentlyViewedProducts(limit)
            }
            products.map { it.toDomainModel() }
        }
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }

    companion object {
        @Volatile private var instance: InMemoryRecentlyViewedProductsRepository? = null

        fun getInstance(database: AppDatabase): InMemoryRecentlyViewedProductsRepository =
            instance ?: synchronized(this) {
                instance ?: InMemoryRecentlyViewedProductsRepository(database).also { instance = it }
            }
    }
}
