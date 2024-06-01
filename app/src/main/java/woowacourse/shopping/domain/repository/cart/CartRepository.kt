package woowacourse.shopping.domain.repository.cart

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct

interface CartRepository {
    fun fetchCartItems(page: Int): List<CartedProduct>

    fun fetchTotalCount(): Int

    fun removeAll()

    fun patchQuantity(productId: Long, quantity: Int, cartItem: CartItem?)

    fun addCartItem(cartItem: CartItem)
}
