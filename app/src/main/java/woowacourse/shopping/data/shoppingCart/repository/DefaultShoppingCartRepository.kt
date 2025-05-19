package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.toEntity
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartStorage
import woowacourse.shopping.data.shoppingCart.storage.VolatileShoppingCartStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultShoppingCartRepository(
    private val shoppingCartStorage: ShoppingCartStorage = VolatileShoppingCartStorage
) : ShoppingCartRepository {
    override var hasNext: Boolean = false
        private set
    override var hasPrevious: Boolean = false
        private set

    override fun load(
        page: Int,
        count: Int,
        result: (Result<List<Product>>) -> Unit
    ) {
        thread {
            val start = (page - 1) * count
            val endExclusive = page * count
            runCatching {
                shoppingCartStorage.load(start, endExclusive).map(ProductEntity::toDomain)
            }.onSuccess { productList ->
                hasNext = endExclusive < shoppingCartStorage.size
                hasPrevious = count < shoppingCartStorage.size && page != 1

                result(Result.success(productList))
            }.onFailure { exception ->
                result(Result.failure(exception))
            }
        }
    }

    override fun add(product: Product, result: (Result<Unit>) -> Unit) {
        thread {
            runCatching {
                shoppingCartStorage.add(product.toEntity())
            }.onSuccess {
                result(Result.success(Unit))
            }.onFailure { exception ->
                result(Result.failure(exception))
            }
        }
    }

    override fun remove(
        product: Product,
        result: (Result<Unit>) -> Unit
    ) {
        thread {
            runCatching {
                shoppingCartStorage.remove(product.toEntity())
            }.onSuccess {
                result(Result.success(Unit))
            }.onFailure { exception ->
                result(Result.failure(exception))
            }
        }
    }
}
