package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.StateFlow
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.Product

interface CartRepository {
    val cartFlow: StateFlow<Cart>

    suspend fun addProduct(
        product: Product,
        quantity: Quantity = Quantity.ONE,
    )

    suspend fun increase(productId: String)

    suspend fun decrease(productId: String)

    suspend fun remove(productId: String)
}
