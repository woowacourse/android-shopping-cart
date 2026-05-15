package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.CartItem

interface CartRepository {
    fun getCart(): Flow<List<CartItem>>

    suspend fun updateCartItem(cartItem: CartItem)

    suspend fun deleteCartItem(productId: String)
}
