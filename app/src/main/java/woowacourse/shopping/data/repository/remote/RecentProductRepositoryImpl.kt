package woowacourse.shopping.data.repository.remote

import woowacourse.shopping.data.datasource.local.RecentProductDataSource
import woowacourse.shopping.data.datasource.remote.ProductDataSource
import woowacourse.shopping.data.entity.RecentlyViewedProduct
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
                    .map { productDataSource.fetchProductById(it.productId) }
            },
            onResult = onResult,
        )
    }

    override fun getMostRecentProduct(onResult: (Result<Product?>) -> Unit) {
        runThread(
            block = {
                recentProductDataSource.getMostRecentProduct()?.toDomain()
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
                val recentProducts = recentProductDataSource.getProducts()
                val productId = product.productId

                if (isNewProduct(recentProducts, productId) && recentProducts.size == 10) {
                    val oldProduct = recentProductDataSource.getOldestProduct()
                    recentProductDataSource.delete(oldProduct)
                }

                recentProductDataSource.insert(product.toEntity())
                Result.success(Unit)
            },
            onResult = onResult,
        )
    }

    private fun isNewProduct(
        recentProducts: List<RecentlyViewedProduct>,
        productId: Long,
    ): Boolean = recentProducts.none { it.productId == productId }

    private fun RecentlyViewedProduct.toDomain(): Product = productDataSource.fetchProductById(this.productId)

    private fun Product.toEntity(): RecentlyViewedProduct =
        RecentlyViewedProduct(
            productId = this.productId,
            viewedAt = System.currentTimeMillis(),
        )
}
