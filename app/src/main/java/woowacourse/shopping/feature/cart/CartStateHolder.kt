package woowacourse.shopping.feature.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.feature.cart.model.toUiModel

class CartStateHolder(private val cartRepository: CartRepository) {
    private val pageSize = 5
    private var currentPage = 0
    private var totalSize = cartRepository.getCartItemCount()

    private val totalPages get() = (totalSize + pageSize - 1) / pageSize

    var uiState: CartUiState by mutableStateOf(CartUiState())
        private set

    init {
        loadPage(0)
    }

    fun nextPage() {
        if (!uiState.isLastPage) loadPage(currentPage + 1)
    }

    fun prevPage() {
        if (!uiState.isFirstPage) loadPage(currentPage - 1)
    }

    fun removeFromCart(productId: String) {
        val updated = cartRepository.getCartItems().remove(productId)
        cartRepository.saveCartItems(updated)
        totalSize = updated.items.size
        loadPage(minOf(currentPage, maxOf(0, totalPages - 1)))
    }

    private fun loadPage(page: Int) {
        currentPage = page
        uiState = CartUiState(
            cartItems = cartRepository.getPagingCartItems(page, pageSize).toUiModel(),
            displayPageNumber = page + 1,
            showControls = totalPages > 1,
            isFirstPage = page == 0,
            isLastPage = totalPages == 0 || page == totalPages - 1,
        )
    }
}

@Composable
fun retainCartStateHolder(): CartStateHolder = retain {
    CartStateHolder(CartRepositoryImpl)
}
