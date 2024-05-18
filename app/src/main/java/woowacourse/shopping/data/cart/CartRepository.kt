package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

interface CartRepository {
    fun increaseQuantity(product: Product)

    fun decreaseQuantity(product: Product)

    fun deleteCartItem(cartItem: CartItem)

    fun deleteAll()

    fun findAll(): List<CartItem>

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun count(): Int
}
