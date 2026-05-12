package woowacourse.shopping.data.datasource.cart

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.cart.CartItemEntity

interface CartDataSource {
    val cartItems: Flow<List<CartItemEntity>>
    suspend fun getCartItem(productId:Int): CartItemEntity?
    suspend fun increaseQuantity(productId:Int, amount: Int): Int
    suspend fun decreaseQuantity(productId: Int): Int
    suspend fun upsert(cartItem: CartItemEntity)
    suspend fun delete(productId:Int)
}
