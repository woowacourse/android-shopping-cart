package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.data.product.storage.VolatileProductsStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = VolatileProductsStorage,
) : ProductsRepository {
    override fun load(
        lastProductId: Long?,
        size: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        thread {
            runCatching {
                productsStorage.load(lastProductId, size).map(ProductEntity::toDomain)
            }.onSuccess { products ->
                onResult(Result.success(products))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }
}
