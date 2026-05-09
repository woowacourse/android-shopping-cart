package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.data.localdb.mapper.toEntity
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product

class CartRepository(
    private val cartItemDao: CartItemDao,
) {
    fun observeCart(): Flow<Cart> =
        cartItemDao
            .getAll()
            .map { entities ->
                Cart(items = entities.map { it.toDomain() })
            }

    suspend fun addItem(
        product: Product,
        quantity: Int,
    ) {
        val cartItem =
            CartItemEntity(
                product.id,
                product.getName(),
                product.getPrice(),
                product.imageUrl,
                quantity,
                System.currentTimeMillis(),
            )
        cartItemDao.insert(cartItem)
    }

    suspend fun increaseQuantity(id: String) {
        val cartItem = cartItemDao.findById(id) ?: return
        val item = cartItem.toDomain()
        cartItemDao.insert(item.increaseQuantity().toEntity(cartItem.timestamp))
    }

    suspend fun decreaseQuantity(id: String) {
        val cartItem = cartItemDao.findById(id) ?: return
        val item = cartItem.toDomain()
        if (item.quantity <= 1) {
            cartItemDao.deleteById(id)
            return
        }

        cartItemDao.insert(item.decreaseQuantity().toEntity(cartItem.timestamp))
    }

    suspend fun deleteItem(id: String) {
        cartItemDao.deleteById(id)
    }

    suspend fun getCartItemQuantity(id: String): Int {
        val item = cartItemDao.findById(id)?.toDomain() ?: return 1
        return item.quantity
    }

    suspend fun getCartSize(): Int = cartItemDao.getTotalCount()

    suspend fun getCartTotalPrice(): Int = cartItemDao.getTotalPrice()
}
