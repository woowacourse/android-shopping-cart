package woowacourse.shopping.ui.screens.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

data class CartUiState(
    val curPage: Int = 1,
    val isLast: Boolean = true,
    val cartItems: List<CartItem> = emptyList(),
)

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        initCartItems()
    }

    fun deleteCartItem(productId: String) {
        viewModelScope.launch {
            cartRepository.deleteItem(productId = productId)
            updateCartItems()
        }
    }

    fun getPrevPage() {
        if (_uiState.value.curPage == 1) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    curPage = _uiState.value.curPage - 1,
                    cartItems = cartRepository.getCartItemByPage(_uiState.value.curPage - 1),
                    isLast = cartRepository.isLastPage(_uiState.value.curPage),
                )
            }
        }
    }

    fun getNextPage() {
        if (_uiState.value.isLast) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    curPage = _uiState.value.curPage + 1,
                    cartItems = cartRepository.getCartItemByPage(_uiState.value.curPage + 1),
                    isLast = cartRepository.isLastPage(_uiState.value.curPage),
                )
            }
        }
    }

    private suspend fun updateCartItems() {
        _uiState.update {
            it.copy(
                cartItems = cartRepository.getCartItemByPage(_uiState.value.curPage),
                isLast = cartRepository.isLastPage(_uiState.value.curPage),
            )
        }

        if (_uiState.value.cartItems.isEmpty()) {
            getPrevPage()
        }
    }

    fun plusCartCount(product: Product) {
        viewModelScope.launch {
            cartRepository.plusItemCount(product)

            updateCartItems()
        }
    }

    fun minusCartCount(productId: String) {
        viewModelScope.launch {
            cartRepository.minusItemCount(productId)

            updateCartItems()
        }
    }

    private fun initCartItems() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cartItems = cartRepository.getCartItemByPage(_uiState.value.curPage),
                    isLast = cartRepository.isLastPage(_uiState.value.curPage),
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                CartViewModel(cartRepository = app.cartRepository)
            }
        }
    }
}
