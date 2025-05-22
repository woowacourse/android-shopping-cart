package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.CartItem

interface ShoppingCartRepository {
    fun load(onLoad: (Result<List<CartItem>>) -> Unit)

    fun add(
        cartItem: CartItem,
        onAdd: (Result<Unit>) -> Unit,
    )

    fun remove(
        cartItem: CartItem,
        onRemove: (Result<Unit>) -> Unit,
    )
}
