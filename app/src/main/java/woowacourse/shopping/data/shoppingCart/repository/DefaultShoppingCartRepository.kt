package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.data.product.entity.CartItemEntity
import woowacourse.shopping.data.product.entity.CartItemEntity.Companion.toEntity
import woowacourse.shopping.data.product.entity.ProductEntity.Companion.toEntity
import woowacourse.shopping.data.shoppingCart.storage.LocalShoppingCartDataSource
import woowacourse.shopping.data.shoppingCart.storage.ShoppingCartDataSource
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultShoppingCartRepository(
    private val shoppingCartDataSource: ShoppingCartDataSource = LocalShoppingCartDataSource,
) : ShoppingCartRepository {
    override fun load(onLoad: (Result<List<CartItem>>) -> Unit) {
        { shoppingCartDataSource.load().map(CartItemEntity::toDomain) }.runAsync(onLoad)
    }

    override fun upsert(
        cartItem: CartItem,
        onAdd: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartDataSource.upsert(cartItem.toEntity()) }.runAsync(onAdd)
    }

    override fun remove(
        cartItem: CartItem,
        onRemove: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartDataSource.remove(cartItem.toEntity()) }.runAsync(onRemove)
    }

    override fun update(
        cartItems: List<CartItem>,
        onUpdate: (Result<Unit>) -> Unit,
    ) {
        { shoppingCartDataSource.update(cartItems.map { it.toEntity() }) }.runAsync(onUpdate)
    }

    override fun quantityOf(
        product: Product,
        onResult: (Result<Int>) -> Unit,
    ) {
        { shoppingCartDataSource.quantityOf(product.toEntity().id) }.runAsync(onResult)
    }

    private inline fun <T> (() -> T).runAsync(crossinline onResult: (Result<T>) -> Unit) {
        thread {
            val result = runCatching(this)
            onResult(result)
        }
    }
}
