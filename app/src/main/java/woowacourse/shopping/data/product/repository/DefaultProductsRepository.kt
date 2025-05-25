package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.dataSource.LocalProductsDataSource
import woowacourse.shopping.data.product.dataSource.ProductsDataSource
import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productsDataSource: ProductsDataSource = LocalProductsDataSource,
) : ProductsRepository {
    override fun load(onLoad: (Result<List<Product>>) -> Unit) {
        thread {
            val result = runCatching { productsDataSource.load().map(ProductEntity::toDomain) }
            onLoad(result)
        }
    }
}
