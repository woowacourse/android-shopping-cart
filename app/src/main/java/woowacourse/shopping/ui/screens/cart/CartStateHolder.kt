package woowacourse.shopping.ui.screens.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartStateHolder(
    private val cartRepository: CartRepository = CartRepositoryImpl(),
) {
    var curPage by mutableIntStateOf(1)
        private set

    var isLast by mutableStateOf(true)
        private set

    var cartItems: List<CartItem> by mutableStateOf(emptyList())
        private set

    init {
        cartItems = cartRepository.getCartItemByPage(curPage)
        isLast = cartRepository.isLastPage(curPage)
    }

    fun deleteCartItem(id: String) {
        cartRepository.deleteItem(id)
        updateCartItems()
    }

    fun getPrevPage() {
        cartItems = cartRepository.getCartItemByPage(--curPage)
        isLast = cartRepository.isLastPage(curPage)
    }

    fun getNextPage() {
        cartItems = cartRepository.getCartItemByPage(++curPage)
        isLast = cartRepository.isLastPage(curPage)
    }

    private fun updateCartItems() {
        cartItems = cartRepository.getCartItemByPage(curPage)
    }
}
