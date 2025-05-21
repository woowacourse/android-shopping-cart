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
        thread {
            val result = runCatching { shoppingCartStorage.load().map(ProductEntity::toDomain) }
            onLoad(result)
        }
    }

    override fun add(
        product: Product,
        onAdd: (Result<Unit>) -> Unit,
    ) {
        thread {
            val result = runCatching { shoppingCartStorage.add(product.toEntity()) }
            onAdd(result)
        }
    }

    override fun remove(
        product: Product,
        onRemove: (Result<Unit>) -> Unit,
    ) {
        thread {
            val result = runCatching { shoppingCartStorage.remove(product.toEntity()) }
            onRemove(result)
        }
    }
}
