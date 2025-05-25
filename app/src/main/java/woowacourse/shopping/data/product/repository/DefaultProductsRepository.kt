package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsDataSource
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.data.LocalProductsDataSource
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
