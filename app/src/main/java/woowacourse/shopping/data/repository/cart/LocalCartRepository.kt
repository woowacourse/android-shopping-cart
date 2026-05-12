package woowacourse.shopping.data.repository.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
        val updatedRowCount = cartItemDao.increaseQuantity(
            productId = product.id,
            amount = quantity.value,
        )
        if (updatedRowCount == 0) {
            cartItemDao.upsert(product.toCartItemEntity(quantity))
        }
    }

    override suspend fun increase(productId: String) {
        cartItemDao.increaseQuantity(productId, amount = 1)
    }

    override suspend fun decrease(productId: String) {
        cartItemDao.decreaseQuantity(productId)
    }

    override suspend fun remove(productId: String) {
        val updatedRows = cartItemDao.decreaseQuantity(productId)
        if(updatedRows == 0){
            cartItemDao.delete(productId)
        }
    }
}
