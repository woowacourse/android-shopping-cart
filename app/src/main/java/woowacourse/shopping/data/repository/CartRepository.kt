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
    fun observeCart(): Flow<CartResult> =
        cartItemDao
            .getAll()
            .map { entities ->
                runCatching {
                    Cart(
                        items =
                            entities.map { entity ->
                                val product = productRepository.getProductById(entity.id)
                                entity.toDomain(product)
                            },
                    )
                }.fold(
                    onSuccess = { CartResult.Success(it) },
                    onFailure = { CartResult.Failure(it) },
                )
            }

    suspend fun setQuantity(
        product: Product,
        quantity: Int,
    ) {
        require(quantity >= 0) { "Quantity must be 0 or greater." }

        if (quantity == 0) {
            cartItemDao.deleteById(product.id)
            return
        }

        val cartItem =
            CartItemEntity(
                product.id,
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

    suspend fun getCartTotalPrice(): Result<Int> =
        runCatching {
            cartItemDao
                .getAll()
                .first()
                .sumOf { entity ->
                    productRepository.getProductById(entity.id).getPrice() * entity.quantity
                }
        }
}
