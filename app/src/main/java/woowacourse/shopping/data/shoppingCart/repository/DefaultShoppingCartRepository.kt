package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.toEntity
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartStorage
import woowacourse.shopping.data.shoppingCart.storage.VolatileShoppingCartStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultShoppingCartRepository(
    private val shoppingCartStorage: ShoppingCartStorage = VolatileShoppingCartStorage,
) : ShoppingCartRepository {
    override fun load(
        offset: Int,
        limit: Int,
        result: (Result<List<Product>>) -> Unit,
    ) {
        thread {
            runCatching {
                shoppingCartStorage.load(offset, offset + limit).map(ProductEntity::toDomain)
            }.onSuccess { productList ->
                result(Result.success(productList))
            }.onFailure { exception ->
                result(Result.failure(exception))
            }
        }
    }

    override fun add(
        product: Product,
        result: (Result<Unit>) -> Unit,
    ) {
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
        result: (Result<Unit>) -> Unit,
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
