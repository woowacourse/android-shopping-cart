package woowacourse.shopping.ui.screens.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.CartItems
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRecentRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ui.screens.util.toUiModel

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productRecentRepository: ProductRecentRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private var offset = 0
    private val cartState: StateFlow<CartItems> = cartRepository
        .getAllCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CartItems(emptyList(), true),
        )

    private val recentProductState: StateFlow<List<RecentProduct>> = productRecentRepository
        .getRecentProducts(RECENT_PRODUCT_LIMIT)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )

    init {
        observeCartChanged()
        observeRecentProductsChanged()
        loadProducts()
    }

    private fun loadProducts() =
        launchWithLoading {
            val products = productRepository.getProducts(offset)
            offset += products.items.size

            _uiState.update { state ->
                state.copy(
                    products = products.items.map { it.toUiModel(cartState.value) },
                    hasNext = products.hasNext,
                )
            }
        }

    private fun observeCartChanged() {
        viewModelScope.launch {
            cartState.collect { updatedCart ->
                _uiState.update { state ->
                    state.copy(
                        products = state.products.map { uiModel ->
                            val amount = updatedCart.getCartItemAmount(uiModel.id)
                            uiModel.copy(
                                cartAmount = amount.toString(),
                                showAmountController = amount > 0,
                            )
                        },
                        totalCartAmount = updatedCart.getTotalAmount(),
                    )
                }
            }
        }
    }

    private fun observeRecentProductsChanged() {
        viewModelScope.launch {
            recentProductState.collect { updatedRecentProducts ->
                _uiState.update { state ->
                    state.copy(
                        recentProducts = updatedRecentProducts,
                        showRecentProducts = updatedRecentProducts.isNotEmpty(),
                    )
                }
            }
        }
    }

    fun getMoreProducts() {
        if (!_uiState.value.hasNext) return
        launchWithLoading {
            val newProducts =
                productRepository.getProducts(offset)
            offset += newProducts.items.size

            val productItems = newProducts.items.map { it.toUiModel(cartState.value) }
            _uiState.update {
                it.copy(
                    products = it.products + productItems,
                    hasNext = newProducts.hasNext,
                )
            }
        }
    }

    fun minusAmount(productId: String) =
        launchWithLoading {
            cartRepository.minusItemAmount(productId)
        }

    fun addAmount(productId: String) =
        launchWithLoading {
            cartRepository.addItem(productId, 1)
        }

    private fun launchWithLoading(action: suspend () -> Unit) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                action()
            } catch (e: Exception) {
                Log.e("ProductViewModel", e.message.toString())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        private const val RECENT_PRODUCT_LIMIT = 10
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ShoppingApplication)
                ProductViewModel(
                    productRepository = application.productRepository,
                    cartRepository = application.cartRepository,
                    productRecentRepository = application.productRecentRepository,
                )
            }
        }
    }
}
