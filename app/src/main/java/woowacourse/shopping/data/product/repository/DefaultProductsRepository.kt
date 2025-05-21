package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.data.product.storage.VolatileProductsStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = VolatileProductsStorage,
) : ProductsRepository {
    override fun load(onLoad: (Result<List<Product>>) -> Unit) {
        thread {
            val result = runCatching { productsStorage.load().map(ProductEntity::toDomain) }
            onLoad(result)
        }
    }
}
