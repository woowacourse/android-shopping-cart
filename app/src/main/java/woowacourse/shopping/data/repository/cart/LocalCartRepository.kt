package woowacourse.shopping.data.repository.cart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import woowacourse.shopping.data.local.cart.CartItemDao
import woowacourse.shopping.data.mapper.toCartItemEntity
import woowacourse.shopping.data.mapper.toDomainCart
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository

class LocalCartRepository(
    private val cartItemDao: CartItemDao,
) : CartRepository {

    override val cartFlow: Flow<Cart> =
        cartItemDao
            .getCartItems()
            .map { it.toDomainCart() }

    override suspend fun addProduct(
        product: Product,
        quantity: Quantity,
    ) {
        val existing = cartItemDao.getCartItem(product.id)
        val nextQuantity =
            if (existing == null) {
                quantity
            } else {
                Quantity(existing.quantity + quantity.value)
            }
        cartItemDao.upsert(product.toCartItemEntity(nextQuantity))
    }

    override suspend fun increase(productId: String) {
        val existing = cartItemDao.getCartItem(productId) ?: return
        cartItemDao.upsert(
            existing.copy(quantity = existing.quantity + 1),
        )
    }

    override suspend fun decrease(productId: String) {
        val existing = cartItemDao.getCartItem(productId) ?: return
        val nextQuantity = existing.quantity - 1
        if (nextQuantity <= Quantity.MIN_VALUE) {
            cartItemDao.delete(productId)
            return
        }
        cartItemDao.upsert(existing.copy(quantity = nextQuantity))
    }

    override suspend fun remove(productId: String) {
        cartItemDao.delete(productId)
    }
}
