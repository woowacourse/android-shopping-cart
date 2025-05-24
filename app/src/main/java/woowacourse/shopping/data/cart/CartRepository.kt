package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem

interface CartRepository {
    fun getAll(): List<CartItem>

    fun add(cartItem: CartItem)

    fun remove(productId: Long)

    fun update(
        productId: Long,
        quantity: Int,
    )

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<CartItem>

    fun clear()

    fun findQuantityById(productId: Long): Int
}
