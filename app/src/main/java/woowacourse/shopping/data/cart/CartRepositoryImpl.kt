package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem

object CartRepositoryImpl : CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    override fun getAll(): List<CartItem> = cartItems.toList()

    override fun add(cartItem: CartItem) {
        cartItems.add(cartItem)
    }

    override fun remove(productId: Long) {
        cartItems.removeIf { it.product.id == productId }
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
        val index = cartItems.indexOfFirst { it.product.id == productId }
        if (index != -1) {
            cartItems[index] = cartItems[index].copy(quantity = quantity)
        }
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

    override fun findQuantityById(productId: Long): Int = cartItems.find { it.product.id == productId }?.quantity ?: 1
}
