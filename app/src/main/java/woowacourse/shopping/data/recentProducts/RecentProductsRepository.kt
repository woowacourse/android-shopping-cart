package woowacourse.shopping.data.recentProducts

import woowacourse.shopping.model.cart.CartItem

interface RecentProductsRepository {
    fun getAll(callback: (Result<List<CartItem>>) -> Unit)

    fun add(
        cartItem: CartItem,
        callback: (Result<Unit>) -> Unit,
    )

    fun remove(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun getSecondMostRecentProduct(callback: (Result<CartItem>) -> Unit)

    fun update(
        productId: Long,
        quantity: Int,
        callback: (Result<Unit>) -> Unit,
    )

    fun findRecentProductById(
        productId: Long,
        callback: (Result<CartItem>) -> Unit,
    )
}
