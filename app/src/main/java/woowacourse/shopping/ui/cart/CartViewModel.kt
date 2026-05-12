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
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.ceil
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var currentCart: Cart = Cart()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.cartFlow.collect { cart ->
                currentCart = cart
                updateUiState(cart)
            }
        }
    }

    fun removeCartItem(productId: String) {
        viewModelScope.launch {
            cartRepository.remove(productId)
        }
    }

    fun increase(productId: String) {
        viewModelScope.launch {
            cartRepository.increase(productId)
        }
    }

    fun decrease(productId: String) {
        viewModelScope.launch {
            cartRepository.decrease(productId)
        }
    }

    fun goToNextPage() {
        val current = _uiState.value as? CartUiState.Success ?: return
        if (!current.hasNext) return
        currentPage++
        updateUiState(currentCart)
    }

    fun goToPreviousPage() {
        val current = _uiState.value as? CartUiState.Success ?: return
        if (!current.hasPrevious) return
        currentPage--
        updateUiState(currentCart)
    }

    private fun updateUiState(cart: Cart) {
        if (cart.isEmpty) {
            _uiState.value = CartUiState.Empty
            return
        }

        val totalPages = max(1, ceil(cart.cartItems.size().toDouble() / PAGE_SIZE).toInt())
        if (currentPage >= totalPages) currentPage = totalPages - 1
        if (currentPage < 0) currentPage = 0

        val pageItems = cart.getPage(currentPage, PAGE_SIZE)

        _uiState.value =
            CartUiState.Success(
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
