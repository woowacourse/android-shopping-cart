package woowacourse.shopping.ui.shopping

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

private const val PAGE_SIZE = 20
private const val RECENT_PRODUCT_LIMIT = 10

class ShoppingViewModel(
    private val productRepository: ProductRepository = ShoppingRepositoryProvider.productRepository,
    private val cartRepository: CartRepository = ShoppingRepositoryProvider.cartRepository,
    private val recentProductRepository: RecentProductRepository = ShoppingRepositoryProvider.recentProductRepository,
    private val networkMonitor: NetworkMonitor = ShoppingRepositoryProvider.networkMonitor,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState(isLoading = true))
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private var visibleCount = PAGE_SIZE

    init {
        observeNetworkState()
        loadProducts()
    }

    fun reloadVisibleState() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            runCatching {
                refreshRecentProducts()
                refreshCartState()
            }.onFailure { throwable ->
                _uiState.value =
                    _uiState.value.copy(
                        errorMessage = throwable.message,
                    )
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            refreshProducts()
        }
    }

    suspend fun refreshProducts() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        runCatching {
            val visibleProducts = productRepository.getProducts(0, visibleCount).toList()
            val hasNext = productRepository.hasNext(visibleProducts.count() - 1)
            val cartState = createCartState(visibleProducts)
            val restoredRecentProducts = getRecentProducts()

            _uiState.value =
                ShoppingUiState(
                    products = cartState.products,
                    recentProducts = restoredRecentProducts,
                    cartQuantity = cartState.cartQuantity,
                    hasNext = hasNext,
                    isLoading = false,
                    isNetworkConnected = _uiState.value.isNetworkConnected,
                    errorMessage = null,
                )
        }.onFailure { throwable ->
            _uiState.value =
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = throwable.message,
                )
        }
    }

    private suspend fun refreshRecentProducts() {
        _uiState.update { currentState ->
            currentState.copy(
                recentProducts = getRecentProducts(),
                errorMessage = null,
            )
        }
    }

    private suspend fun refreshCartState() {
        val visibleProducts = _uiState.value.products.map { it.product }
        val cartState = createCartState(visibleProducts)

        _uiState.update { currentState ->
            currentState.copy(
                products = cartState.products,
                cartQuantity = cartState.cartQuantity,
                errorMessage = null,
            )
        }
    }

    private suspend fun getRecentProducts() =
        recentProductRepository
            .getRecentProducts(RECENT_PRODUCT_LIMIT)
            .let { recentProducts ->
                val recentProductsById = productRepository.findAllByIds(recentProducts.map { it.productId }.toSet())
                RecentViewedProductsMapper.toProducts(
                    recentProducts = recentProducts,
                    productsById = recentProductsById,
                )
            }

    private suspend fun createCartState(visibleProducts: List<woowacourse.shopping.model.Product>): ShoppingCartState {
        val cartItems = cartRepository.getCartItems(0, cartRepository.count())
        val cartQuantity = cartItems.sumOf { it.quantity }
        val visibleCartItems = cartRepository.getCartItemsByProductIds(visibleProducts.map { it.id }.toSet())
        val quantityByProductId = visibleCartItems.associate { it.productId to it.quantity }

        return ShoppingCartState(
            products =
                ShoppingProductUiStateMapper.toUiStates(
                    products = visibleProducts,
                    quantityByProductId = quantityByProductId,
                ),
            cartQuantity = cartQuantity,
        )
    }

    private data class ShoppingCartState(
        val products: List<ShoppingProductUiState>,
        val cartQuantity: Int,
    )

    fun loadMore() {
        val currentState = _uiState.value
        if (currentState.isLoading || !currentState.hasNext) return
        visibleCount = minOf(visibleCount + PAGE_SIZE, productRepository.size)
        loadProducts()
    }

    fun addToCart(productId: ProductId) = increaseQuantity(productId)

    fun increaseQuantity(productId: ProductId) {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            runCatching {
                cartRepository.add(productId)
                refreshProducts()
            }.onFailure { throwable ->
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                    )
            }
        }
    }

    fun decreaseQuantity(productId: ProductId) {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            runCatching {
                cartRepository.delete(productId)
                refreshProducts()
            }.onFailure { throwable ->
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                    )
            }
        }
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
