package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.repository.cart.CartRepository
import kotlin.math.ceil
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

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
        val current = _uiState.value as? CartUiState.Success ?: return
        if (!current.hasNext) return
        currentPage++
        loadCart()
    }

    fun goToPreviousPage() {
        val current = _uiState.value as? CartUiState.Success ?: return
        if (!current.hasPrevious) return
        currentPage--
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            runCatching { cartRepository.getCart() }
                .onSuccess { cart ->
                    _uiState.value = mapToUiState(cart)
                }
                .onFailure { throwable ->
                    _uiState.value = CartUiState.Error(throwable)
                }
        }
    }

    private fun mapToUiState(cart: Cart): CartUiState {
        if (cart.totalCount == 0) return CartUiState.Empty

        val totalPages = max(1, ceil(cart.totalCount.toDouble() / PAGE_SIZE).toInt())
        if (currentPage >= totalPages) {
            currentPage = max(0, totalPages - 1)
        }

        val pageItems = cart.getPage(currentPage, PAGE_SIZE)

        return CartUiState.Success(
            cartItems = pageItems,
            currentPage = currentPage,
            totalPages = totalPages,
            hasPrevious = currentPage > 0,
            hasNext = currentPage < totalPages - 1,
        )
    }

    companion object {
        private const val PAGE_SIZE = 5

        fun factory(cartRepository: CartRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    CartViewModel(cartRepository)
                }
            }

    }
}
