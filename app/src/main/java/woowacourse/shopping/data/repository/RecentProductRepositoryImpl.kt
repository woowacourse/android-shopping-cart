package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.db.RecentProductEntity
import woowacourse.shopping.data.model.toProduct
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val recentProductLocalDataSource: RecentProductLocalDataSource,
    private val recentProductLimit: Int = 10,
) : RecentProductRepository {
    override fun getRecentProducts(
        limit: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) = runCatchingInThread(onResult) {
        val recentProducts = recentProductLocalDataSource.getRecentProducts(limit)
        val products =
            productRemoteDataSource.findProductsByIds(recentProducts.map { it.productId })
        products.map { it.toProduct() }
    }

    override fun insertAndTrimToLimit(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        recentProductLocalDataSource.insertRecentProduct(RecentProductEntity(productId))
        recentProductLocalDataSource.trimToLimit(recentProductLimit)
    }
}
