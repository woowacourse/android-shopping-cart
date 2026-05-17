package woowacourse.shopping.data.repository.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.datasource.cart.CartDataSource
import woowacourse.shopping.data.mapper.toCartItemEntity
import woowacourse.shopping.data.mapper.toDomainCart
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository

class LocalCartRepository(
    private val cartDataSource: CartDataSource,
) : CartRepository {

    override val cartFlow: Flow<Cart> =
        cartDataSource
            .cartItems
            .map { it.toDomainCart() }


    override suspend fun addProduct(
        product: Product,
        quantity: Quantity,
    ) {
        val updatedRowCount = cartDataSource.increaseQuantity(
            productId = product.id,
            amount = quantity.value,
        )
        if (updatedRowCount == 0) {
            cartDataSource.upsert(product.toCartItemEntity(quantity))
        }
    }

    override suspend fun increase(productId: Int) {
        cartDataSource.increaseQuantity(productId, amount = 1)
    }

    override suspend fun decrease(productId: Int) {
        val updatedRows = cartDataSource.decreaseQuantity(productId)
        if (updatedRows == 0) {
            cartDataSource.delete(productId)
        }
    }

    override suspend fun remove(productId: Int) {
        cartDataSource.delete(productId)
    }
}
