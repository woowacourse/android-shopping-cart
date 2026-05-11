package woowacourse.shopping.repository

import kotlinx.coroutines.flow.StateFlow
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import java.util.UUID

interface CartRepository {
    val cart: Cart
    val cartFlow: StateFlow<Cart>

    suspend fun addProduct(product: Product, amount: Int = 1)
    suspend fun decreaseProduct(productId: UUID, amount: Int = 1)
    suspend fun removeProduct(productId: UUID)
}
