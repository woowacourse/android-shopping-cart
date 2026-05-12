package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity

class CartRepository(
    private val cartItemDao: CartItemDao,
) {
    fun observeCartItems(): Flow<List<CartItemEntity>> = cartItemDao.getAll()

    suspend fun setQuantity(
        id: String,
        quantity: Int,
    ) {
        require(quantity >= 0) { "Quantity must be 0 or greater." }

        if (quantity == 0) {
            cartItemDao.deleteById(id)
            return
        }

        val cartItem =
            CartItemEntity(
                id,
                quantity,
                System.currentTimeMillis(),
            )
        cartItemDao.insert(cartItem)
    }

    suspend fun updateQuantity(
        id: String,
        quantity: Int,
    ) {
        require(quantity >= 0) { "수량은 0 이상이어야 합니다." }

        if (quantity == 0) {
            cartItemDao.deleteById(id)
            return
        }

        val cartItem = cartItemDao.findById(id) ?: return
        cartItemDao.insert(cartItem.copy(quantity = quantity))
    }

    suspend fun deleteItem(id: String) {
        cartItemDao.deleteById(id)
    }

    suspend fun getCartItemQuantity(id: String): Int? = cartItemDao.findById(id)?.quantity

    suspend fun getCartSize(): Int = cartItemDao.getTotalCount()
}
