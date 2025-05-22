package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem

interface CartRepository {
    fun getAll(): List<CartItem>

    fun add(cartItem: CartItem)

    fun remove(cartItem: CartItem)

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<CartItem>

    fun clear()
}
