package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.cart.Cart
import kotlin.math.min

class FakeCartRepository(
    private val carts: MutableList<Cart>,
) : CartRepository {
    private var id: Long = 7

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ) {
        carts.add(Cart(id++, 1, productId))
    }

    override fun plusCartItem(
        productId: Long,
        quantity: Int,
    ) {
        val cartItem = carts[productId.toInt() - 1]

        println(carts)

        carts[productId.toInt() - 1] =
            cartItem.copy(
                quantity = cartItem.quantity + 1,
            )

        println(carts)
    }

    override fun minusCartItem(
        productId: Long,
        quantity: Int,
    ) {
        val cartItem = carts[productId.toInt() - 1]
        carts[productId.toInt() - 1] =
            cartItem.copy(
                quantity = cartItem.quantity - 1,
            )
    }

    override fun removeAllCartItem(productId: Long) {
        carts.removeIf { it.id == productId }
    }

    override fun fetchAllCart(): List<Cart>? {
        return carts
    }

    override fun fetchTotalCartCount(): Int {
        return carts.size
    }

    override fun fetchCartItem(productId: Long): Cart {
        return carts.find { it.productId == productId } ?: Cart(0, 0, 0)
    }

    override fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<Cart> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, carts.size)

        if (fromIndex > toIndex) return emptyList()

        return carts.subList(fromIndex, toIndex)
    }
}
