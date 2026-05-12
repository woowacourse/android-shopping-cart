package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import woowacourse.shopping.repository.ShoppingRepositoryProvider
import woowacourse.shopping.ui.common.recentlyviewed.RecentViewedProductsMapper

class ProductDetailViewModel(
    private val productRepository: ProductRepository = ShoppingRepositoryProvider.productRepository,
    private val cartRepository: CartRepository = ShoppingRepositoryProvider.cartRepository,
    private val recentProductRepository: RecentProductRepository = ShoppingRepositoryProvider.recentProductRepository,
    private val networkMonitor: NetworkMonitor = ShoppingRepositoryProvider.networkMonitor,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        observeNetworkState()
    }

    fun loadProduct(productId: ProductId) {
        viewModelScope.launch {
            runCatching {
                val product = productRepository.findAllByIds(setOf(productId))[productId] ?: return@runCatching
                recentProductRepository.recordView(product.id)
                refreshProductDetail(product.id)
            }.onFailure { throwable ->
                _uiState.value =
                    _uiState.value.copy(
                        isAdding = false,
                        errorMessage = throwable.message,
                    )
            }
        }
    }

    fun addToCart() {
        increaseQuantity()
    }

    fun increaseQuantity() {
        val product = _uiState.value.product ?: return
        if (_uiState.value.isAdding) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAdding = true, errorMessage = null)

            runCatching {
                cartRepository.add(product.id)
                refreshProductDetail(product.id)
            }.onFailure { throwable ->
                _uiState.value =
                    _uiState.value.copy(
                        isAdding = false,
                        errorMessage = throwable.message,
                    )
            }
        }
    }

    fun decreaseQuantity() {
        val product = _uiState.value.product ?: return
        if (_uiState.value.isAdding || _uiState.value.quantity == 0) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAdding = true, errorMessage = null)

            runCatching {
                cartRepository.delete(product.id)
                refreshProductDetail(product.id)
            }.onFailure { throwable ->
                _uiState.value =
                    _uiState.value.copy(
                        isAdding = false,
                        errorMessage = throwable.message,
                    )
            }
        }
    }

    private suspend fun refreshProductDetail(productId: ProductId) {
        val product = productRepository.findAllByIds(setOf(productId))[productId] ?: return
        val lastViewedRecentProduct = recentProductRepository.getLatestViewedProductExcluding(productId)
        val lastViewedProductsById =
            productRepository.findAllByIds(
                listOfNotNull(lastViewedRecentProduct?.productId).toSet(),
            )
        val lastViewedProduct =
            RecentViewedProductsMapper.toProduct(
                recentProduct = lastViewedRecentProduct,
                productsById = lastViewedProductsById,
            )
        val quantity =
            cartRepository
                .getCartItemsByProductIds(setOf(productId))
                .firstOrNull()
                ?.quantity ?: 0

        _uiState.value =
            _uiState.value.copy(
                product = product,
                lastViewedProduct = lastViewedProduct,
                quantity = quantity,
                isAdding = false,
                errorMessage = null,
            )
    }

    private fun observeNetworkState() {
        viewModelScope.launch {
            networkMonitor.isNetworkConnected.collect { isConnected ->
                _uiState.update { currentState ->
                    currentState.copy(isNetworkConnected = isConnected)
                }
            }
        }
    }
}
