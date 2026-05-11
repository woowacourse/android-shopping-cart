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
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct
import woowacourse.shopping.ui.model.toUiModel

data class ProductUiState(
    val products: List<UiProduct> = emptyList(),
    val recentProducts: List<UiRecentProduct> = emptyList(),
    val totalCartCount: Int = 0,
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
)

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private var products: List<Product> = emptyList()

    private var recentProductIds: List<String> = emptyList()

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadInitialProducts()
    }

    fun loadInitialProducts() {
        viewModelScope.launch {
            products = productRepository.getProducts(0, PAGE_SIZE)
            recentProductIds = recentProductRepository.getRecentProductIds()

            updateProducts()
            updateRecentProductIds()
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    products = products.map { product ->
                        product.toUiModel(cartQuantity = cartRepository.getItemCount(product.id))
                    },
                    hasNext = productRepository.productSize > products.size,
                    totalCartCount = cartRepository.getCartItemCount(),
                )
            }
        }
    }

    fun updateRecentProductIds() {
        _uiState.update {
            it.copy(
                recentProducts = recentProductIds.map { productId ->
                    getUiRecentProduct(productId = productId)
                },
            )
        }
    }

    private fun getUiRecentProduct(productId: String): UiRecentProduct {
        val product = productRepository.getProductById(productId)

        return UiRecentProduct(
            id = productId,
            imageUrl = product.imageUrl,
            name = product.name,
        )
    }

    fun getProducts() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                products = products + productRepository
                    .getProducts(products.size, PAGE_SIZE)
                    .distinct()

                updateProducts()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun plusCartCount(productId: String) {
        val product = productRepository.getProductById(productId)

        viewModelScope.launch {
            cartRepository.plusItemCount(product)

            updateProducts()
        }
    }

    fun minusCartCount(productId: String) {
        viewModelScope.launch {
            cartRepository.minusItemCount(productId)

            updateProducts()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                ProductViewModel(
                    productRepository = app.productRepository,
                    cartRepository = app.cartRepository,
                    recentProductRepository = app.recentProductRepository,
                )
            }
        }
    }
}
