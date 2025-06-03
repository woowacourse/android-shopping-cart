package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem

class FakeCartRepository : CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    override fun getCartItems(
        limit: Int,
        offset: Int,
        callback: (List<CartItem>, Boolean) -> Unit,
    ) {
        callback(cartItems, false)
    }

    override fun deleteCartItem(
        id: Long,
        callback: (Long) -> Unit,
    ) {
        val removed = cartItems.removeIf { it.product.id == id }
        if (removed) callback(id)
    }

    override fun addCartItem(
        cartItem: CartItem,
        callback: () -> Unit,
    ) {
        cartItems.add(cartItem)
        callback()
    }

    override fun increaseCartItem(
        productId: Long,
        callback: (CartItem?) -> Unit,
    ) {
        val item = cartItems.find { it.product.id == productId }
        item?.let {
            val updated = it.copy(amount = it.amount + 1)
            cartItems.remove(it)
            cartItems.add(updated)
            callback(updated)
        } ?: callback(null)
    }

    override fun decreaseCartItem(
        productId: Long,
        callback: (CartItem?) -> Unit,
    ) {
        val item = cartItems.find { it.product.id == productId }
        item?.let {
            val updated = it.copy(amount = it.amount - 1)
            cartItems.remove(it)
            if (updated.amount > 0) {
                cartItems.add(updated)
                callback(updated)
            } else {
                callback(null)
            }
        } ?: callback(null)
    }

    fun addTestCartItems(items: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(items)
    }
}
