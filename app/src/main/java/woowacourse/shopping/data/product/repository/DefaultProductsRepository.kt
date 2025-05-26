package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.dataSource.LocalProductsDataSource
import woowacourse.shopping.data.product.dataSource.LocalRecentViewedProductsDataSource
import woowacourse.shopping.data.product.dataSource.ProductsDataSource
import woowacourse.shopping.data.product.dataSource.RecentViewedProductsDataSource
import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productsDataSource: ProductsDataSource = LocalProductsDataSource,
    val recentViewedProductsDataSource: RecentViewedProductsDataSource = LocalRecentViewedProductsDataSource,
) : ProductsRepository {
    override fun load(onLoad: (Result<List<Product>>) -> Unit) {
        { productsDataSource.load().map(ProductEntity::toDomain) }.runAsync(onLoad)
    }

    override fun loadLastViewedProducts(onLoad: (Result<List<Product>>) -> Unit) {
        { recentViewedProductsDataSource.load().map(ProductEntity::toDomain) }.runAsync(onLoad)
    }

    private inline fun <T> (() -> T).runAsync(crossinline onResult: (Result<T>) -> Unit) {
        thread {
            val result = runCatching(this)
            onResult(result)
        }
    }
}
