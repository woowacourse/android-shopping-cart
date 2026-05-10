package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.network.NetworkManager
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository
import woowacourse.shopping.ui.model.mapper.toUiModel

class ShoppingViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentItemRepository: RecentItemRepository,
    private val networkManager: NetworkManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private var offset = 0
    private val pageSize = 20

    init {
        observeNetwork()
        observeCart()
        loadRecentItems()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkManager.observe().collect { isAvailable ->
                _uiState.value =
                    _uiState.value.copy(isNetworkAvailable = isAvailable)

                if (isAvailable && _uiState.value.products.isEmpty()) {
                    loadMore()
                }
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCart().collect { cart ->
                _uiState.value =
                    _uiState.value.copy(
                        cartSize = cart.getTotalQuantity(),
                        cartQuantities = cart.items.associate { it.product.id to it.quantity },
                    )
            }
        }
    }

    fun loadRecentItems() {
        viewModelScope.launch {
            val recentItems =
                recentItemRepository.getRecentItems().map { it.toUiModel() }.toImmutableList()

            _uiState.value = _uiState.value.copy(recentItems = recentItems)
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (!currentState.isNetworkAvailable || !currentState.canLoadMore || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)

            val loadProducts =
                productRepository.getProducts(offset, pageSize).map { it.toUiModel() }

            offset += loadProducts.size

            _uiState.value =
                _uiState.value.copy(
                    products = (currentState.products + loadProducts).toImmutableList(),
                    canLoadMore = loadProducts.size == pageSize,
                    isLoading = false,
                )
        }
    }

    fun increaseQuantity(productId: String) {
        viewModelScope.launch {
            val product = productRepository.getProductById(productId)
            val currentQuantity = _uiState.value.cartQuantities[productId] ?: 0

            if (currentQuantity == 0) {
                cartRepository.addItem(product, quantity = 1)
            } else {
                cartRepository.increaseQuantity(productId)
            }
        }
    }

    fun decreaseQuantity(productId: String) {
        viewModelScope.launch {
            cartRepository.decreaseQuantity(productId)
        }
    }

    companion object {
        fun provideFactory(
            productRepository: ProductRepository,
            cartRepository: CartRepository,
            recentItemRepository: RecentItemRepository,
            networkManager: NetworkManager,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ShoppingViewModel(
                        productRepository = productRepository,
                        cartRepository = cartRepository,
                        recentItemRepository = recentItemRepository,
                        networkManager = networkManager,
                    )
                }
            }
    }
}
