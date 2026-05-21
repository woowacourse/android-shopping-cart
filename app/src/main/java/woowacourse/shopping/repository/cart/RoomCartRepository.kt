package woowacourse.shopping.repository.cart

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.dao.ShoppingDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import java.util.UUID

class RoomCartRepository(
    private val dao: ShoppingDao,
    private val externalScope: CoroutineScope,
) : CartRepository {
    private val _cartFlow = MutableStateFlow(Cart(CartProducts()))
    override val cartFlow: StateFlow<Cart> = _cartFlow.asStateFlow()

    override val cart: Cart get() = cartFlow.value

    init {
        externalScope.launch {
            dao.getCartItemsWithProduct().collect { entities ->
                val domainCartProducts = entities.map { it.toDomain() }
                _cartFlow.value = Cart(CartProducts(domainCartProducts))
            }
        }
    }

    override suspend fun addProduct(
        product: Product,
        amount: Int,
    ) {
        dao.insertProduct(listOf(product.toEntity()))
        // DB에서 직접 수량을 확인하여 레이스 컨디션 방지
        val currentAmount = dao.getCartItemAmount(product.productId)
        if (currentAmount != null) {
            dao.updateCartQuantity(product.productId, currentAmount + amount)
        } else {
            dao.insertCartItem(CartEntity(product.productId, amount))
        }
    }

    override suspend fun decreaseProduct(productId: UUID, amount: Int) {
        val currentAmount = dao.getCartItemAmount(productId) ?: return
        val newAmount = currentAmount - amount
        if (newAmount <= 0) {
            dao.deleteCartItem(productId)
        } else {
            dao.updateCartQuantity(productId, newAmount)
        }
    }

    override suspend fun removeProduct(productId: UUID) {
        dao.deleteCartItem(productId)
    }
}
