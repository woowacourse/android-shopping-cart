package woowacourse.shopping.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.data.localdb.mapper.toEntity
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartRepository(
    private val cartItemDao: CartItemDao,
) {
    fun observeCart(): Flow<Cart> =
        cartItemDao.getAll()
            .map { entities ->
                Cart(items = entities.map { it.toDomain() })
            }

    suspend fun addItem(product: Product) {
        val item = cartItemDao.findById(product.id)?.toDomain()
        val cartItem =
            item?.increaseQuantity()
                ?: CartItem(
                    product = product,
                    quantity = 1,
                )
        cartItemDao.insert(cartItem.toEntity(System.currentTimeMillis()))
    }

    suspend fun increaseQuantity(id: String) {
        val item = cartItemDao.findById(id)?.toDomain() ?: return
        cartItemDao.insert(item.increaseQuantity().toEntity(System.currentTimeMillis()))
    }

    suspend fun decreaseQuantity(id: String) {
        val item = cartItemDao.findById(id)?.toDomain() ?: return

        if (item.quantity <= 1) {
            cartItemDao.deleteById(id)
            return
        }

        cartItemDao.insert(item.decreaseQuantity().toEntity(System.currentTimeMillis()))
    }

    suspend fun deleteItem(id: String) {
        cartItemDao.deleteById(id)
    }

    suspend fun getCartSize(): Int = cartItemDao.getTotalCount()

    suspend fun getCartTotalPrice(): Int = cartItemDao.getTotalPrice()
}
