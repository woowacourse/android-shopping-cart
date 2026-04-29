package woowacourse.shopping.feature.cart.bridge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.data.CartRepository
import woowacourse.shopping.core.data.ProductRepository
import woowacourse.shopping.core.uimodel.CartItemUiModel
import woowacourse.shopping.core.uimodel.toUiModel

class CartBridge(
    private val productRepository: ProductRepository = ProductRepository,
    private val cartRepository: CartRepository = CartRepository,
) {
    var cartItems: ImmutableList<CartItemUiModel> by mutableStateOf(persistentListOf())

    init {
        refresh()
    }

    fun removeFromCart(productId: String) {
        val product = productRepository.getProductById(productId)
        cartRepository.deleteItem(product)
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
