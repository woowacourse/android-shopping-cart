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
                val recentlyViewedProduct =
                    recentProductDataSource
                        .getProducts()
                        .getOrDefault(emptyList())

                val products =
                    recentlyViewedProduct
                        .map {
                            productDataSource.fetchProductById(it.productId).getOrThrow()
                        }
                Result.success(products)
            },
            onResult = onResult,
        )
    }

    override fun getMostRecentProduct(onResult: (Result<Product?>) -> Unit) {
        runThread(
            block = {
                val recentProduct = recentProductDataSource.getMostRecentProduct().getOrNull()
                recentProduct?.toProduct() ?: Result.success(null)
            },
            onResult = onResult,
        )
    }

    override fun insertRecentProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runThread(
            block = {
                val recentProducts = recentProductDataSource.getProducts().getOrThrow()
                val recentProductSize = recentProductDataSource.getCount().getOrThrow()
                val productId = product.productId

                if (isNewProduct(recentProducts, productId) && recentProductSize == 10) {
                    val oldProduct = recentProductDataSource.getOldestProduct().getOrThrow()
                    recentProductDataSource.delete(oldProduct)
                }

                recentProductDataSource.insert(product.toEntity())
            },
            onResult = onResult,
        )
    }

    private fun isNewProduct(
        recentProducts: List<RecentlyViewedProduct>,
        productId: Long,
    ): Boolean = recentProducts.none { it.productId == productId }

    private fun RecentlyViewedProduct.toProduct(): Result<Product?> = productDataSource.fetchProductById(this.productId)

    private fun Product.toEntity(): RecentlyViewedProduct =
        RecentlyViewedProduct(
            productId = this.productId,
            viewedAt = System.currentTimeMillis(),
        )
}
