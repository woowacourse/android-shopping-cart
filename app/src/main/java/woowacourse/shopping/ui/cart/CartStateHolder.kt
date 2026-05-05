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
) {
    var page by mutableIntStateOf(initialPage)
    var currentCartItems by mutableStateOf(emptyList<CartItemUiModel>())
    var isCanMoveNext by mutableStateOf(false)

    init {
        getCartItems()
    }

    fun getTotalCartSize(): Int = cartRepository.getCartSize()

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
        val cartPage =
            cartRepository.getCartPage(
                page = page,
                pageSize = 5,
            )

        currentCartItems = cartPage.items.map { it.toUiModel() }
        isCanMoveNext = cartPage.isCanMoveNext
        page = cartPage.page

        onPageChanged(page)
    }
}
