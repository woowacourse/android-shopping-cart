package woowacourse.shopping.ui.screens.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartStateHolder(
    initialPage: Int = 1,
    private val cartRepository: CartRepository = CartRepositoryImpl(),
) {
    var curPage by mutableIntStateOf(initialPage)
        private set

    var isLast by mutableStateOf(true)
        private set

    var cartItems: List<CartItem> by mutableStateOf(emptyList())
        private set

    suspend fun initCartItems() {
        cartItems = cartRepository.getCartItemByPage(curPage)
        isLast = cartRepository.isLastPage(curPage)
    }

    suspend fun deleteCartItem(id: String) {
        cartRepository.deleteItem(id)
        updateCartItems()
    }

    suspend fun getPrevPage() {
        if (curPage == 1) return

        cartItems = cartRepository.getCartItemByPage(--curPage)
        isLast = cartRepository.isLastPage(curPage)
    }

    suspend fun getNextPage() {
        if (isLast) return

        cartItems = cartRepository.getCartItemByPage(++curPage)
        isLast = cartRepository.isLastPage(curPage)
    }

    private suspend fun updateCartItems() {
        cartItems = cartRepository.getCartItemByPage(curPage)
        isLast = cartRepository.isLastPage(curPage)

        if (cartItems.isEmpty()) {
            getPrevPage()
        }
    }

    companion object {
        val Saver: Saver<CartStateHolder, Int> = Saver(
            save = { it.curPage },
            restore = { CartStateHolder(it) },
        )
    }
}
