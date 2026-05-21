package woowacourse.shopping.repository.cart

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import java.util.UUID

object InMemoryCartRepository : CartRepository {
    private val _cartFlow = MutableStateFlow(Cart(CartProducts(emptyList())))
    override val cartFlow: StateFlow<Cart> = _cartFlow.asStateFlow()

    override val cart: Cart get() = _cartFlow.value

    override suspend fun addProduct(
        product: Product,
        amount: Int,
    ) {
        _cartFlow.value = _cartFlow.value.addProduct(product, amount)
    }

    override suspend fun decreaseProduct(productId: UUID, amount: Int) {
        _cartFlow.value = _cartFlow.value.decreaseProduct(productId, amount)
    }

    override suspend fun removeProduct(productId: UUID) {
        _cartFlow.value = _cartFlow.value.removeProduct(productId)
    }
}
