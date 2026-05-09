package woowacourse.shopping.ui.screens.product

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
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.toUiModel

data class ProductUiState(
    val products: List<UiProduct> = emptyList(),
    val totalCartCount: Int = 0,
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
)

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private var products: List<Product> = emptyList()

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadInitialProducts()
    }

    fun loadInitialProducts() {
        viewModelScope.launch {
            products = productRepository.getProducts(0, PAGE_SIZE)

            updateProducts()
        }
    }

    fun updateProducts() {
        _uiState.update {
            it.copy(
                products = products.map { product ->
                    product.toUiModel(cartQuantity = cartRepository.getItemCount(product.id))
                },
                hasNext = productRepository.productSize > products.size,
                totalCartCount = cartRepository.cartItemCount,
            )
        }
    }

    fun getProducts() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                products = (products + productRepository.getProducts(products.size, PAGE_SIZE))
                    .distinct()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun getCartItemCount(productId: String): Int = cartRepository.getItemCount(productId = productId)

    fun plusCartCount(productId: String) {
        val product = productRepository.getProductById(productId)

        cartRepository.plusItemCount(product)

        updateProducts()
    }

    fun minusCartCount(productId: String) {
        cartRepository.minusItemCount(productId)

        updateProducts()
    }

    companion object {
        private const val PAGE_SIZE = 20

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                ProductViewModel(
                    productRepository = app.productRepository,
                    cartRepository = app.cartRepository,
                )
            }
        }
    }
}
