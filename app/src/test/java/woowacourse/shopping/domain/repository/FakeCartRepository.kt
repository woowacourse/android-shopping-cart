package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem
import kotlin.math.min

class FakeCartRepository(
    private val cartItems: MutableList<CartItem>,
) : CartRepository {
    private var id: Long = 14

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        return id++
    }

    override fun plusCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        val cartItem = cartItems[productId.toInt() - 1]
        cartItems[productId.toInt() - 1] =
            cartItem.copy(
                quantity = cartItem.quantity + 1,
            )
        return productId
    }

    override fun removeCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        val cartItem = cartItems[productId.toInt() - 1]
        cartItems[productId.toInt() - 1] =
            cartItem.copy(
                quantity = cartItem.quantity - 1,
            )
        return productId
    }

    override fun removeAllCartItem(productId: Long): Long {
        cartItems.removeIf { it.id == productId }
        return productId
    }

    override fun fetchCartItem(productId: Long): CartItem? {
        return cartItems.first { it.productId == productId }
    }

    override fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cartItems.size)

        if (fromIndex > toIndex) return emptyList()

        return cartItems.subList(fromIndex, toIndex)
    }
}
