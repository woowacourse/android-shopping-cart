package woowacourse.shopping.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.ui.model.CartItemUiModel
import woowacourse.shopping.ui.model.toUiModel
import kotlin.math.min

class CartStateHolder(
    private val cartRepository: CartRepository = CartRepository,
) {
    var page by mutableIntStateOf(0)
    var currentCartItems by mutableStateOf(emptyList<CartItemUiModel>())
    var isCanMoveNext by mutableStateOf(false)

    private val pageSize = 5

    init {
        getCartItems()
    }

    fun getTotalCartSize(): Int = cartRepository.getCart().items.size

    fun removeFromCart(productId: String) {
        cartRepository.deleteItem(productId)
        getCartItems()
    }

    fun getCartItems() {
        val items = cartRepository.getCart().items.map { it.toUiModel() }
        val lastPage =
            if (items.isEmpty()) {
                0
            } else {
                items.lastIndex / pageSize
            }
        if (page < 0) {
            page = 0
        } else if (page > lastPage) {
            page = lastPage
        }
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, items.size)
        currentCartItems = items.subList(fromIndex, toIndex)
        isCanMoveNext = toIndex < items.size
    }

    fun nextPage() {
        page++
        getCartItems()
    }

    fun previousPage() {
        page--
        getCartItems()
    }
}
