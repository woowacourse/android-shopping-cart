package woowacourse.shopping.domain.repository.cart

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct

interface CartRepository {
    fun fetchCartItems(page: Int): List<CartedProduct>

//    fun addCartItem(cartItem: CartItem)

    fun fetchTotalCount(): Int

//    fun updateQuantity(
//        cartItemId: Long,
//        quantity: Int,
//    )
//
//    fun removeCartItem(cartItem: CartItem)

    fun removeAll()

    fun patchQuantity(productId: Long, quantity: Int, cartItem: CartItem?)
}
