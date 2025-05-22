package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.ProductEntity.Companion.toEntity
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartStorage
import woowacourse.shopping.data.shoppingCart.storage.VolatileShoppingCartStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultShoppingCartRepository(
    private val shoppingCartStorage: ShoppingCartStorage = VolatileShoppingCartStorage,
) : ShoppingCartRepository {
    override fun load(onLoad: (Result<List<Product>>) -> Unit) {
        { shoppingCartStorage.load().map(ProductEntity::toDomain) }.runAsync(onLoad)
    }

    override fun add(
        product: Product,
        onAdd: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartStorage.add(product.toEntity()) }.runAsync(onAdd)
    }

    override fun remove(
        product: Product,
        onRemove: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartStorage.remove(product.toEntity()) }.runAsync(onRemove)
    }

    private inline fun <T> (() -> T).runAsync(crossinline onResult: (Result<T>) -> Unit) {
        thread {
            val result = runCatching(this)
            onResult(result)
        }
    }
}
