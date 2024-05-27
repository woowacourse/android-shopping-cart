package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct

interface CartDataSource {
    fun fetchCartItems(page: Int): List<CartedProduct>
    fun addCartItem(cartItem: CartItem)
    fun fetchTotalCount(): Int
    fun updateQuantity(cartItemId: Long, quantity: Int)
    fun removeCartItem(cartItem: CartItem)
    fun removeAll()
}
