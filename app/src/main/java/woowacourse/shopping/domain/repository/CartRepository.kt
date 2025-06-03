package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem

interface CartRepository {
    fun getCartItems(
        limit: Int,
        offset: Int,
        callback: (List<CartItem>, Boolean) -> Unit,
    )

    fun deleteCartItem(
        id: Long,
        callback: (Long) -> Unit,
    )

    fun addCartItem(
        cartItem: CartItem,
        callback: () -> (Unit),
    )

    fun increaseCartItem(
        productId: Long,
        callback: (CartItem?) -> (Unit),
    )

    fun decreaseCartItem(
        productId: Long,
        callback: (CartItem?) -> (Unit),
    )
}
