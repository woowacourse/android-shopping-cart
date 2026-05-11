package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product

class CartRepository(
    private val cartItemDao: CartItemDao,
    private val productRepository: ProductRepository,
) {
    fun observeCart(): Flow<Cart> =
        cartItemDao
            .getAll()
            .map { entities ->
                Cart(
                    items =
                        entities.mapNotNull { entity ->
                            val product =
                                runCatching {
                                    productRepository.getProductById(entity.id)
                                }.getOrNull()

                            product?.let { entity.toDomain(it) }
                        },
                )
            }

    suspend fun addItem(
        product: Product,
        quantity: Int,
    ) {
        val cartItem =
            CartItemEntity(
                product.id,
                quantity,
                System.currentTimeMillis(),
            )
        cartItemDao.insert(cartItem)
    }

    suspend fun increaseQuantity(id: String) {
        val cartItem = cartItemDao.findById(id) ?: return
        cartItemDao.insert(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    suspend fun decreaseQuantity(id: String) {
        val cartItem = cartItemDao.findById(id) ?: return
        if (cartItem.quantity <= 1) {
            cartItemDao.deleteById(id)
            return
        }

        cartItemDao.insert(cartItem.copy(quantity = cartItem.quantity - 1))
    }

    suspend fun deleteItem(id: String) {
        cartItemDao.deleteById(id)
    }

    suspend fun getCartItemQuantity(id: String): Int {
        return cartItemDao.findById(id)?.quantity ?: 1
    }

    suspend fun getCartSize(): Int = cartItemDao.getTotalCount()

    suspend fun getCartTotalPrice(): Int =
        cartItemDao
            .getAll()
            .first()
            .sumOf { entity ->
                runCatching {
                    productRepository.getProductById(entity.id).getPrice() * entity.quantity
                }.getOrDefault(0)
            }
}
