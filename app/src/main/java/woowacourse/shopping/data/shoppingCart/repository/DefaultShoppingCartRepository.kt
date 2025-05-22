package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.CartItemEntity
import woowacourse.shopping.data.product.entity.CartItemEntity.Companion.toEntity
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartStorage
import woowacourse.shopping.data.shoppingCart.storage.VolatileShoppingCartStorage
import woowacourse.shopping.domain.product.CartItem
import kotlin.concurrent.thread

class DefaultShoppingCartRepository(
    private val shoppingCartStorage: ShoppingCartStorage = VolatileShoppingCartStorage,
) : ShoppingCartRepository {
    override fun load(onLoad: (Result<List<CartItem>>) -> Unit) {
        { shoppingCartStorage.load().map(CartItemEntity::toDomain) }.runAsync(onLoad)
    }

    override fun add(
        cartItem: CartItem,
        onAdd: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartStorage.add(cartItem.toEntity()) }.runAsync(onAdd)
    }

    override fun remove(
        cartItem: CartItem,
        onRemove: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartStorage.remove(cartItem.toEntity()) }.runAsync(onRemove)
    }

    fun update(cartItems: List<CartItem>) {
        shoppingCartStorage.update(cartItems.map { it.toEntity() })
    }

    private inline fun <T> (() -> T).runAsync(crossinline onResult: (Result<T>) -> Unit) {
        thread {
            val result = runCatching(this)
            onResult(result)
        }
    }
}
