package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.cart.CartItem
import kotlin.math.min

class FakeCartRepository : CartRepository {
    private val cartItems: MutableList<CartItem> =
        mutableListOf(
            CartItem(1, 1, 1),
            CartItem(2, 2, 1),
            CartItem(3, 3, 1),
            CartItem(4, 4, 1),
            CartItem(5, 5, 1),
            CartItem(6, 6, 1),
            CartItem(7, 7, 1),
            CartItem(8, 8, 1),
            CartItem(9, 9, 1),
            CartItem(10, 10, 1),
            CartItem(11, 11, 1),
            CartItem(12, 12, 1),
            CartItem(13, 13, 1),
        )
    private var id: Long = 14

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        cartItems.add(
            CartItem(
                id = id,
                productId = productId,
                quantity = quantity,
            ),
        )
        return id++
    }

    override fun removeCartItem(cartItemId: Long): Long {
        cartItems.removeIf { it.id == cartItemId }
        return cartItemId
    }

    override fun fetchCartItems(page: Int): List<CartItem> {
        val fromIndex = page * 5
        val toIndex = min(fromIndex + 5, cartItems.size)
        return cartItems.subList(fromIndex, toIndex)
    }
}
