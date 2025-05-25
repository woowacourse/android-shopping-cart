package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem

interface CartRepository {
    fun getAll(callback: (List<CartItem>) -> Unit)

    fun add(
        cartItem: CartItem,
        callback: () -> Unit,
    )

    fun remove(
        productId: Long,
        callback: () -> Unit,
    )

    fun update(
        productId: Long,
        quantityIncrease: Int,
    )

    fun fetchProducts(
        offset: Int,
        limit: Int,
        callback: (List<CartItem>) -> Unit,
    )

    fun clear()

    fun findQuantityById(
        productId: Long,
        callback: (Int) -> Unit,
    )
}
