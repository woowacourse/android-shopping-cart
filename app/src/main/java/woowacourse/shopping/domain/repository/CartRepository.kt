package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage

interface CartRepository {
    fun getCart(
        id: Long,
        onResult: (cart: Cart?) -> Unit,
    )

    fun getCarts(
        productIds: List<Long>,
        onResult: (carts: List<Cart?>) -> Unit,
    )

    fun upsert(
        productId: Long,
        quantity: Quantity,
    )

    fun modify(
        id: Long,
        quantity: Quantity,
    )

    fun delete(
        id: Long,
        onResult: (() -> Unit)? = null,
    )

    fun singlePage(
        page: Int,
        pageSize: Int,
        onResult: (CartSinglePage) -> Unit,
    )
}
