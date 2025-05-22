package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.db.RecentProductEntity
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
            .map { productDataSource.findProductById(it.productId) }
    }

    override fun insertAndTrimToLimit(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        recentProductLocalDataSource.insertRecentProduct(RecentProductEntity(productId))
        trimRecentProductsToLimit()
    }

    override fun deleteByProductId(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        recentProductLocalDataSource.deleteByProductId(productId)
    }

    private fun trimRecentProductsToLimit() {
        val recentProducts = recentProductLocalDataSource.getRecentProducts(RECENT_PRODUCT_LIMIT)
        if (recentProducts.size > RECENT_PRODUCT_LIMIT) {
            val overflowProducts = recentProducts.dropLast(recentProducts.size - RECENT_PRODUCT_LIMIT)
            overflowProducts.forEach { recentProductLocalDataSource.deleteByProductId(it.productId) }
        }
    }

    companion object {
        private const val RECENT_PRODUCT_LIMIT = 10
    }
}
