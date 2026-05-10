package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.cart.CartItems

interface CartRepository {
    fun getCartItems(): Flow<CartItems>

    fun getCartItem(productId: String): Flow<CartItem?>

    suspend fun updateCart(cartItem: CartItem)

    suspend fun deleteCartItem(productId: String)

    suspend fun increaseCartItemQuantity(productId: String)

    suspend fun decreaseCartItemQuantity(productId: String)

    suspend fun getCartItemCount(): Int

    suspend fun getPagingCartItems(
        page: Int,
        pageSize: Int = 5,
    ): CartItems
}
