package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem

interface CartRepository {
    fun increaseCartItemQuantity(productId: Long)

    fun decreaseCartItemQuantity(productId: Long)

    fun deleteCartItemAtOnce(cartItemId: Long)

    fun deleteAll()

    fun findAll(): List<CartItem>

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun count(): Int
}
