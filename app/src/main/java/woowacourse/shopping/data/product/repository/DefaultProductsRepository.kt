package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.data.product.storage.VolatileProductsStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = VolatileProductsStorage,
) : ProductsRepository {
    override var loadable: Boolean = false
        private set

    override fun load(
        lastProductId: Long?,
        size: Int,
        result: (Result<List<Product>>) -> Unit
    ) {
        thread {
            runCatching {
                productsStorage.load(lastProductId, size).map(ProductEntity::toDomain)
            }.onSuccess { products ->
                loadable =
                    if (products.lastOrNull() == null) {
                        true
                    } else {
                        products.last().id != productsStorage.lastProductId
                    }

                result(Result.success(products))
            }.onFailure { exception ->
                result(Result.failure(exception))
            }
        }
    }
}
