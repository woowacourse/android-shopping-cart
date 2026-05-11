package woowacourse.shopping.presentation.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.network.NetworkMonitor
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val networkMonitor: NetworkMonitor,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProductListUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            networkMonitor.isOnline.collect { isOnline ->
                val wasOffline = _uiState.value.isOnline

                _uiState.update { it.copy(isOnline = isOnline) }

                if (wasOffline && !isOnline) {
                    _uiEvent.send(ProductListUiEvent.ShowMessage("네트워크 연결이 끊겼습니다."))
                }

                if (wasOffline && isOnline) {
                    loadPages()
                    refreshCart()
                }
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            if (!_uiState.value.hasNextPage) return@launch

            val nextPageIndex = _uiState.value.currentPageIndex + 1

            val nextProducts =
                productRepository.getPagingProducts(
                    page = nextPageIndex,
                    pageSize = pageSize,
                )

            val hasNextPage =
                productRepository.hasNextPage(
                    currentPage = nextPageIndex,
                    pageSize = pageSize,
                )

            _uiState.update {
                it.copy(
                    currentPageIndex = nextPageIndex,
                    products = it.products + nextProducts,
                    hasNextPage = hasNextPage,
                )
            }
        }
    }

    fun increaseQuantity(product: Product) {
        viewModelScope.launch {
            cartRepository.increaseQuantity(product, 1)
            refreshCart()
        }
    }

    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.decreaseQuantity(productId)
            refreshCart()
        }
    }

    fun refreshCart() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cart = cartRepository.getItems(),
                )
            }
        }
    }

    fun refreshRecentlyViewedProducts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    recentlyViewedProducts = recentlyViewedProductRepository.getRecentlyViewedProducts(),
                )
            }
        }
    }

    private fun loadPages() {
        viewModelScope.launch {
            var products = Products()

            for (page in 0.._uiState.value.currentPageIndex) {
                products +=
                    productRepository.getPagingProducts(
                        page = page,
                        pageSize = pageSize,
                    )
            }

            val hasNextPage =
                productRepository.hasNextPage(
                    currentPage = _uiState.value.currentPageIndex,
                    pageSize = pageSize,
                )

            _uiState.update {
                it.copy(
                    products = products,
                    hasNextPage = hasNextPage,
                )
            }
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}

class ProductListViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val networkMonitor: NetworkMonitor,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
                recentlyViewedProductRepository = recentlyViewedProductRepository,
                networkMonitor = networkMonitor,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
