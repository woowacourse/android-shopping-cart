package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.db.RecentProductEntity
import woowacourse.shopping.data.model.toProduct
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val recentProductLocalDataSource: RecentProductLocalDataSource,
) : RecentProductRepository {
    override fun getRecentProducts(
        limit: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) = runCatchingInThread(onResult) {
        recentProductLocalDataSource
            .getRecentProducts(limit)
            .map { productDataSource.findProductById(it.productId).toProduct() }
    }

    override fun insertAndTrimToLimit(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        recentProductLocalDataSource.insertRecentProduct(RecentProductEntity(productId))
        trimRecentProductsToLimit()
    }

    private fun trimRecentProductsToLimit() {
        val savedRecentProductCount = recentProductLocalDataSource.getRecentProductCount()
        if (savedRecentProductCount > RECENT_PRODUCT_LIMIT) {
            val overflowCount = savedRecentProductCount - RECENT_PRODUCT_LIMIT
            repeat(overflowCount) {
                recentProductLocalDataSource.deleteOldestRecentProduct()
            }
        }
    }

    companion object {
        private const val RECENT_PRODUCT_LIMIT = 10
    }
}
