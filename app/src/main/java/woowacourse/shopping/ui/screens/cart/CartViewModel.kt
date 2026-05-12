package woowacourse.shopping.ui.screens.cart

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
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ui.model.UiCart
import woowacourse.shopping.ui.model.toUiModel
import woowacourse.shopping.ui.util.NetworkMonitor

data class CartUiState(
    val curPage: Int = 1,
    val isLast: Boolean = true,
    val cartItems: List<UiCart> = emptyList(),
    val isNetworkConnected: Boolean = true,
)

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { isConnected ->
                _uiState.update { it.copy(isNetworkConnected = isConnected) }
            }
        }

        initCartItems()
    }

    fun deleteCartItem(productId: String) {
        viewModelScope.launch {
            cartRepository.deleteItem(productId = productId)
            loadCartUiState()
        }
    }

    fun loadPrevPage() {
        if (_uiState.value.curPage == 1) return

        viewModelScope.launch {
            val page = _uiState.value.curPage - 1

            _uiState.update {
                it.copy(
                    curPage = page,
                    cartItems = cartRepository
                        .getCartItemByPage(page)
                        .map { cartItem ->
                            cartItem.toUiModel(productRepository.getProductById(cartItem.productId))
                        },
                    isLast = cartRepository.isLastPage(page),
                )
            }
        }
    }

    fun loadNextPage() {
        if (_uiState.value.isLast) return

        viewModelScope.launch {
            val page = _uiState.value.curPage + 1

            _uiState.update {
                it.copy(
                    curPage = page,
                    cartItems = cartRepository
                        .getCartItemByPage(page)
                        .map { cartItem ->
                            cartItem.toUiModel(productRepository.getProductById(cartItem.productId))
                        },
                    isLast = cartRepository.isLastPage(page),
                )
            }
        }
    }

    private suspend fun loadCartUiState() {
        _uiState.update {
            it.copy(
                cartItems = cartRepository
                    .getCartItemByPage(_uiState.value.curPage)
                    .map { cartItem ->
                        cartItem.toUiModel(productRepository.getProductById(cartItem.productId))
                    },
                isLast = cartRepository.isLastPage(_uiState.value.curPage),
            )
        }

        if (_uiState.value.cartItems.isEmpty()) {
            loadPrevPage()
        }
    }

    fun plusCartCount(product: Product) {
        viewModelScope.launch {
            cartRepository.plusItemCount(product)

            loadCartUiState()
        }
    }

    fun minusCartCount(productId: String) {
        viewModelScope.launch {
            cartRepository.minusItemCount(productId)

            loadCartUiState()
        }
    }

    private fun initCartItems() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cartItems = cartRepository
                        .getCartItemByPage(_uiState.value.curPage)
                        .map { cartItem ->
                            cartItem.toUiModel(productRepository.getProductById(cartItem.productId))
                        },
                    isLast = cartRepository.isLastPage(_uiState.value.curPage),
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                CartViewModel(
                    cartRepository = app.cartRepository,
                    productRepository = app.productRepository,
                    networkMonitor = app.networkMonitor,
                )
            }
        }
    }
}
