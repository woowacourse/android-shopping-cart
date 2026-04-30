package woowacourse.shopping.feature.cart.bridge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.repository.CartRepository
import woowacourse.shopping.core.repository.MockRepository
import woowacourse.shopping.core.repository.ProductRepository
import woowacourse.shopping.core.uimodel.CartItemUiModel
import woowacourse.shopping.core.uimodel.toUiModel

class CartBridge(
    private val productRepository: ProductRepository = MockRepository(),
    private val cartRepository: CartRepository = CartRepository,
) {
    var cartItems: ImmutableList<CartItemUiModel> by mutableStateOf(persistentListOf())

    init {
        refresh()
    }

    fun removeFromCart(productId: String) {
        cartRepository.deleteItem(productId)
        refresh()
    }

    private fun refresh() {
        cartItems =
            cartRepository
                .getCart()
                .items
                .map { it.toUiModel() }
                .toImmutableList()
    }
}
