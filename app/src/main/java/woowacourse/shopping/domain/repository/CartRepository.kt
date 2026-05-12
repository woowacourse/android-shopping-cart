package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.Product

interface CartRepository {
    val cartFlow: Flow<Cart>

    suspend fun addProduct(
        product: Product,
        quantity: Quantity = Quantity.ONE,
    )

    suspend fun increase(productId: Int)

    suspend fun decrease(productId: Int)

    suspend fun remove(productId: Int)
}
