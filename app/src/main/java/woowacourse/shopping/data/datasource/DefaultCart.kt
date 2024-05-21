package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.CartItem
import kotlin.math.min

object DefaultCart : CartDataSource {
    private val cartItems: MutableMap<Long, CartItem> = mutableMapOf()
    private var id: Long = 1

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        val existingCartItem = cartItems[productId]
        if (existingCartItem == null) {
            cartItems[productId] =
                CartItem(
                    id = id,
                    quantity = quantity,
                    productId = productId,
                )
            id++
        } else {
            cartItems[productId] =
                existingCartItem.copy(
                    quantity = quantity + 1,
                )
        }
        return productId
    }

    override fun deleteCartItem(cartItemId: Long): Long {
        cartItems.values.removeIf { it.id == cartItemId }
        return cartItemId
    }

    override fun deleteAll() {
        cartItems.clear()
    }

    override fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cartItems.size)

        if (fromIndex > toIndex) return emptyList()

        return cartItems.values.toList().subList(fromIndex, toIndex)
    }
}
