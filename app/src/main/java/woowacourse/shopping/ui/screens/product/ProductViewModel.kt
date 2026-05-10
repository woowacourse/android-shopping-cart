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
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
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

    init {
        observeCartChanged()
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

    private fun Product.toUiModel(cartItems: CartItems): ProductUiModel {
        val amount = cartItems.getCartItemAmount(this.id)
        return ProductUiModel(
            id = id,
            name = name,
            price = price.toPriceFormat(),
            imageUrl = imageUrl,
            cartAmount = amount.toString(),
            showAmountController = amount > 0,
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ShoppingApplication)
                ProductViewModel(
                    productRepository = application.productRepository,
                    cartRepository = application.cartRepository,
                )
            }
        }
    }
}

fun Int.toPriceFormat(): String = "${"%,d".format(this)}원"
