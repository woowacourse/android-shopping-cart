package woowacourse.shopping.data.datasource.cart

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.cart.CartItemDao
import woowacourse.shopping.data.local.cart.CartItemEntity

class RoomCartDataSource (
    private val cartItemDao: CartItemDao
) : CartDataSource {
    override val cartItems: Flow<List<CartItemEntity>> = cartItemDao.getCartItems()
    override suspend fun getCartItem(productId: String) = cartItemDao.getCartItem(productId)
    override suspend fun increaseQuantity(productId:String, amount: Int) = cartItemDao.increaseQuantity(productId, amount)
    override suspend fun decreaseQuantity(productId:String) = cartItemDao.decreaseQuantity(productId)
    override suspend fun upsert(cartItem: CartItemEntity) = cartItemDao.upsert(cartItem)
    override suspend fun delete(productId: String) = cartItemDao.delete(productId)
}
