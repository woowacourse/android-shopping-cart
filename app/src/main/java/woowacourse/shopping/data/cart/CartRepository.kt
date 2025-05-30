package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem

interface CartRepository {
    fun getAll(callback: (Result<List<CartItem>>) -> Unit)

    fun add(
        cartItem: CartItem,
        callback: (Result<Unit>) -> Unit,
    )

    fun remove(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun update(
        productId: Long,
        quantityIncrease: Int,
        callback: (Result<Unit>) -> Unit,
    )

    fun fetchProducts(
        offset: Int,
        limit: Int,
        callback: (Result<List<CartItem>>) -> Unit,
    )

    fun clear()

    fun findQuantityById(
        productId: Long,
        callback: (Result<Int>) -> Unit,
    )
}
