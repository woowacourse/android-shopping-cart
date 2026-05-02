package woowacourse.shopping.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryMockImpl
import kotlin.math.ceil
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository = CartRepositoryMockImpl,
) : ViewModel() {
    private val cartUiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = cartUiState.asStateFlow()

    private var currentPage = 0

    init {
        loadCart()
    }

    fun removeCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeCartItem(cartItem)
            loadCart()
        }
    }

    fun goToNextPage() {
        if (!cartUiState.value.hasNext) return
        currentPage++
        loadCart()
    }

    fun goToPreviousPage() {
        if (!cartUiState.value.hasPrevious) return
        currentPage--
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            val cart = cartRepository.getCart()
            val totalPages = max(1, ceil(cart.totalCount.toDouble() / PAGE_SIZE).toInt())

            if (currentPage >= totalPages) {
                currentPage = max(0, totalPages - 1)
            }

            val pageItems = cart.getPage(currentPage, PAGE_SIZE)

            cartUiState.value =
                CartUiState(
                    cartItems = pageItems,
                    currentPage = currentPage,
                    totalPages = totalPages,
                    hasPrevious = currentPage > 0,
                    hasNext = currentPage < totalPages - 1,
                )
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
