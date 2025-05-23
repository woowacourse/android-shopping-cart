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

    override fun getMostRecentProduct(onResult: (Result<Product?>) -> Unit) {
        runThread(
            block = { recentProductDataSource.getMostRecentProduct()?.toProduct() },
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
            },
            onResult = onResult,
        )
    }

    private fun isNewProduct(
        recentProducts: List<RecentlyViewedProduct>,
        productId: Long,
    ): Boolean = recentProducts.none { it.productId == productId }

    private fun RecentlyViewedProduct.toProduct(): Product = productDataSource.getProductById(this.productId)

    private fun Product.toEntity(): RecentlyViewedProduct =
        RecentlyViewedProduct(
            productId = this.productId,
            viewedAt = System.currentTimeMillis(),
        )
}
