package woowacourse.shopping.ui.screens.productdetail

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
import woowacourse.shopping.ui.model.UiLastViewProduct
import woowacourse.shopping.ui.util.NetworkMonitor

data class ProductDetailUiState(
    val product: Product? = null,
    val recentProduct: UiLastViewProduct? = null,
    val isNetworkConnected: Boolean = true,
    val quantity: Int = 1,
)

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val networkMonitor: NetworkMonitor,
    private val targetProductId: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { isConnected ->
                _uiState.update { it.copy(isNetworkConnected = isConnected) }
            }
        }
        loadProduct()
        loadLastViewProduct()
        addRecentProductId(targetProductId)
    }

    private fun loadProduct() {
        viewModelScope.launch {
            val product = productRepository.getProductById(targetProductId)
            _uiState.update { it.copy(product = product) }
        }
    }

    private fun loadLastViewProduct() {
        viewModelScope.launch {
            val id = recentProductRepository.getLastViewProductId()

            val recentProduct = if (id == null) {
                null
            } else {
                UiLastViewProduct(
                    id = id,
                    name = productRepository.getProductById(id).name,
                )
            }

            _uiState.update {
                it.copy(
                    recentProduct = recentProduct,
                )
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            cartRepository.addItem(targetProductId, _uiState.value.quantity)
        }
    }

    fun plusCartCount() {
        _uiState.update {
            it.copy(
                quantity = it.quantity + 1,
            )
        }
    }

    fun minusCartCount() {
        if (_uiState.value.quantity == 1) return

        _uiState.update {
            it.copy(
                quantity = it.quantity - 1,
            )
        }
    }

    private fun addRecentProductId(productId: String) {
        viewModelScope.launch {
            recentProductRepository.addRecentProductId(productId = productId)
        }
    }

    companion object {
        fun factory(productId: String): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val app = this[APPLICATION_KEY] as ShoppingApplication
                    ProductDetailViewModel(
                        productRepository = app.appContainer.productRepository,
                        cartRepository = app.appContainer.cartRepository,
                        recentProductRepository = app.appContainer.recentProductRepository,
                        networkMonitor = app.appContainer.networkMonitor,
                        targetProductId = productId,
                    )
                }
            }
    }
}
