package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem

object CartRepositoryImpl : CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    override fun getAll(): List<CartItem> = cartItems.toList()

    override fun add(cartItem: CartItem) {
        cartItems.add(cartItem)
    }

    override fun remove(cartItem: CartItem) {
        cartItems.remove(cartItem)
    }

    override fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<CartItem> {
        val end = (offset + limit).coerceAtMost(cartItems.size)
        return cartItems.subList(offset, end)
    }

    override fun clear() {
        cartItems.clear()
    }
}
