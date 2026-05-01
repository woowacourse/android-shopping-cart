package woowacourse.shopping.feature.cart.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.core.repository.CartRepository
import woowacourse.shopping.core.uimodel.CartItemUiModel
import woowacourse.shopping.core.uimodel.toUiModel
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

    fun getTotalCartSize(): Int = cartRepository.getTotalCartSize()

    fun getCartItems() {
        val items = cartRepository.getCart().items.map { it.toUiModel() }
        val maxPage = if (items.isEmpty()) 0 else (items.size - 1) / pageSize
        page = page.coerceIn(0, maxPage)

        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, items.size)
        currentCartItems = items.subList(fromIndex, toIndex)
        isCanMoveNext = toIndex < items.size
    }

    fun nextPage() {
        if (!isCanMoveNext) return
        page++
        getCartItems()
    }

    fun previousPage() {
        if (page == 0) return
        page--
        getCartItems()
    }
}
