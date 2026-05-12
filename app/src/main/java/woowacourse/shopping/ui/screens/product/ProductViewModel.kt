package woowacourse.shopping.ui.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
import woowacourse.shopping.ui.util.NetworkMonitor

data class ProductUiState(
    val products: List<UiProduct> = emptyList(),
    val recentProducts: List<UiRecentProduct> = emptyList(),
    val totalCartCount: Int = 0,
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
    val isNetworkConnected: Boolean = true,
)

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {
    private var products: List<Product> = emptyList()

    private var recentProductIds: List<String> = emptyList()

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { isConnected ->
                _uiState.update { it.copy(isNetworkConnected = isConnected) }
            }
        }

        loadProducts()
    }

    fun loadProducts() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val nextProducts = productRepository.getProducts(products.size, PAGE_SIZE)
                products = (products + nextProducts).distinctBy { it.id }
                recentProductIds = recentProductRepository.getRecentProductIds()

                loadProductUiState()
                loadRecentProductUiState()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun plusCartCount(productId: String) {
        viewModelScope.launch {
            val product = productRepository.getProductById(productId)
            cartRepository.plusItemCount(product)

            loadProductUiState()
        }
    }

    fun minusCartCount(productId: String) {
        viewModelScope.launch {
            cartRepository.minusItemCount(productId)

            loadProductUiState()
        }
    }

    private fun loadProductUiState() {
        viewModelScope.launch {
            val totalSize = productRepository.getProductSize()
            _uiState.update {
                it.copy(
                    products = products.map { product ->
                        product.toUiModel(cartQuantity = cartRepository.getItemCount(product.id))
                    },
                    hasNext = totalSize > products.size,
                    totalCartCount = cartRepository.getCartItemCount(),
                )
            }
        }
    }

    private fun loadRecentProductUiState() {
        viewModelScope.launch {
            val uiRecentProducts = recentProductIds
                .map { productId ->
                    async {
                        val product = productRepository.getProductById(productId)
                        UiRecentProduct(
                            id = productId,
                            imageUrl = product.imageUrl,
                            name = product.name,
                        )
                    }
                }.awaitAll()

            _uiState.update {
                it.copy(
                    recentProducts = uiRecentProducts,
                )
            }
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
                    networkMonitor = app.networkMonitor,
                )
            }
        }
    }
}
