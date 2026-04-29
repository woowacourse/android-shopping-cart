package woowacourse.shopping.ui.screens.cart

import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository

class CartStateHolder(
    private val cartRepository: CartRepository = CartRepositoryImpl(),
) {
    val cartItems = cartRepository.getCartItems()

    fun deleteCartItem(id: String) {
        cartRepository.deleteItem(id)
    }
}
