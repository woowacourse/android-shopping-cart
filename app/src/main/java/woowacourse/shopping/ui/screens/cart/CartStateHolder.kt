package woowacourse.shopping.ui.screens.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository

class CartStateHolder(
    private val cartRepository: CartRepository = CartRepositoryImpl(),
) {
    var cartItems by mutableStateOf(cartRepository.getCartItems())
        private set

    fun deleteCartItem(id: String) {
        cartRepository.deleteItem(id)
        updateCartItems()
    }

    private fun updateCartItems() {
        cartItems = cartRepository.getCartItems()
    }
}
