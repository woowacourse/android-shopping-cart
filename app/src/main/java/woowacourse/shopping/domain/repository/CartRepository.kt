package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.cart.CartItems

interface CartRepository {
    fun getCartItems(): Flow<CartItems>

    fun getCartItem(productId: String): Flow<CartItem?>

    fun updateCart(cartItem: CartItem)

    fun deleteCartItem(productId: String)

    fun increaseCartItemQuantity(productId: String)

    fun decreaseCartItemQuantity(productId: String)

    fun getCartItemCount(): Int

    fun getPagingCartItems(
        page: Int,
        pageSize: Int = 5,
    ): CartItems

    fun saveCartItems(cartItems: CartItems)
}
