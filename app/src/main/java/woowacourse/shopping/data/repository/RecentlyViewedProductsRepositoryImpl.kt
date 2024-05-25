package woowacourse.shopping.data.repository

import woowacourse.shopping.data.database.RecentlyProductDatabase
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.mapper.toEntityModel
import woowacourse.shopping.data.model.RecentlyViewedProductEntity
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository

class RecentlyViewedProductsRepositoryImpl(
    recentlyProductDatabase: RecentlyProductDatabase,
) : RecentlyViewedProductsRepository {
    private val recentlyProductDao = recentlyProductDatabase.recentlyProductDao()

    override fun insertRecentlyViewedProduct(product: RecentlyViewedProduct) {
        threadAction {
            val existingProduct =
                recentlyProductDao.findRecentlyViewedProductById(product.productId)
            if (existingProduct != null) {
                val updatedProduct = existingProduct.copy(viewedAt = System.currentTimeMillis())
                recentlyProductDao.insertRecentlyViewedProduct(updatedProduct)
            } else {
                recentlyProductDao.insertRecentlyViewedProduct(product.toEntityModel())
            }
        }
    }

    override fun getRecentlyViewedProducts(limit: Int): List<RecentlyViewedProduct> {
        var products = emptyList<RecentlyViewedProductEntity>()
        threadAction {
            products = recentlyProductDao.getRecentlyViewedProducts(limit)
        }
        return products.map { it.toDomainModel() }
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
