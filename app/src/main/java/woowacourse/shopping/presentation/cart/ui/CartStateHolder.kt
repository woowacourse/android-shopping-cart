package woowacourse.shopping.presentation.cart.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.InMemoryCartRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.cart.model.CartItemUiModel
import woowacourse.shopping.presentation.cart.model.toUiModel
import kotlin.math.min

class CartStateHolder(
    private val cartRepository: CartRepository = InMemoryCartRepository,
) {
    var page by mutableIntStateOf(0)
    var currentCartItems by mutableStateOf(emptyList<CartItemUiModel>())
    var isCanMoveNext by mutableStateOf(false)
    var totalCartSize by mutableIntStateOf(0)
    var isLoading by mutableStateOf(false)

    private val pageSize = 5

    suspend fun loadCartItems() {
        if (isLoading) return
        isLoading = true
        try {
            val cart = cartRepository.getCart()
            val items = cart.items.map { it.toUiModel() }
            totalCartSize = items.size

            val maxPage = if (items.isEmpty()) 0 else (items.size - 1) / pageSize
            page = page.coerceIn(0, maxPage)

            val fromIndex = page * pageSize
            val toIndex = min(fromIndex + pageSize, items.size)

            currentCartItems = items.subList(fromIndex, toIndex)
            isCanMoveNext = toIndex < items.size
        } finally {
            isLoading = false
        }
    }

    suspend fun deleteItem(productId: String) {
        cartRepository.deleteItem(productId)
        loadCartItems()
    }

    suspend fun nextPage() {
        if (!isCanMoveNext) return
        page++
        loadCartItems()
    }

    suspend fun previousPage() {
        if (page == 0) return
        page--
        loadCartItems()
    }
}
