package woowacourse.shopping.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.ui.model.CartItemUiModel
import woowacourse.shopping.ui.model.toUiModel

class CartStateHolder(
    initialPage: Int = 0,
    private val onPageChanged: (Int) -> Unit = {},
    private val cartRepository: CartRepository = CartRepository,
    private val cartPageLoader: CartPageLoader = CartPageLoader(),
) {
    var page by mutableIntStateOf(initialPage)
    var currentCartItems by mutableStateOf(emptyList<CartItemUiModel>())
    var isCanMoveNext by mutableStateOf(false)

    init {
        getCartItems()
    }

    fun getTotalCartSize(): Int = cartRepository.getCart().items.size

    fun removeFromCart(productId: String) {
        cartRepository.deleteItem(productId)
        getCartItems()
    }

    fun nextPage() {
        page++
        getCartItems()
    }

    fun previousPage() {
        page--
        getCartItems()
    }

    private fun getCartItems() {
        val items = cartRepository.getCart().items.map { it.toUiModel() }
        val cartPage = cartPageLoader.getCartPage(
            page = page,
            items = items
        )

        currentCartItems = cartPage.items
        isCanMoveNext = cartPage.isCanMoveNext
        page = cartPage.page

        onPageChanged(page)
    }
}
