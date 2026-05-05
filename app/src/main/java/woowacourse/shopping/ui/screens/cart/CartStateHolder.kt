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

    var isLoading by mutableStateOf(false)
        private set

    suspend fun initCartItems() {
        if (isLoading) return
        isLoading = true

        cartItems = cartRepository.getCartItemByPage(curPage)
        isLast = cartRepository.isLastPage(curPage)

        isLoading = false
    }

    suspend fun deleteCartItem(id: String) {
        if (isLoading) return
        isLoading = true

        cartRepository.deleteItem(id)
        updateCartItems()

        isLoading = false
    }

    suspend fun getPrevPage() {
        if (curPage == 1 || isLoading) return
        isLoading = true

        cartItems = cartRepository.getCartItemByPage(--curPage)
        isLast = cartRepository.isLastPage(curPage)
        isLoading = false
    }

    suspend fun getNextPage() {
        if (isLast || isLoading) return
        isLoading = true

        cartItems = cartRepository.getCartItemByPage(++curPage)
        isLast = cartRepository.isLastPage(curPage)

        isLoading = false
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
