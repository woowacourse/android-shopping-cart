package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.data.db.RecentlyViewedProduct
import woowacourse.shopping.data.runThread
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val recentProductDataSource: RecentProductDataSource,
    private val productDataSource: ProductDataSource,
) : RecentProductRepository {
    override fun getRecentProducts(onResult: (Result<List<Product>>) -> Unit) {
        runThread(
            block = {
                recentProductDataSource
                    .getProducts()
                    .map { productDataSource.getProductById(it.productId) }
            },
            onResult = onResult,
        )
    }

    override fun insertRecentProduct(
        product: RecentlyViewedProduct,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = {
                val count = recentProductDataSource.getProducts().size

                if (count == 10) {
                    val oldest = recentProductDataSource.getOldestProduct()
                    if (oldest != null && oldest.productId != product.productId) {
                        recentProductDataSource.delete(oldest)
                    }
                }
                recentProductDataSource.insert(product)
            },
            onResult = onResult,
        )
    }
}
