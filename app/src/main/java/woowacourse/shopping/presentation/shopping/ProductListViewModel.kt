package woowacourse.shopping.presentation.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    val hasNextPage: Boolean
        get() =
            productRepository.hasNextPage(
                currentPage = _uiState.value.currentPageIndex,
                pageSize = pageSize,
            )

    init {
        loadPages()
        refreshCart()
    }

    fun loadMore() {
        if (!hasNextPage) return

        val nextPageIndex = _uiState.value.currentPageIndex + 1

        val nextProducts =
            productRepository.getPagingProducts(
                page = nextPageIndex,
                pageSize = pageSize,
            )

        _uiState.update {
            it.copy(
                currentPageIndex = nextPageIndex,
                products = it.products + nextProducts,
            )
        }
    }

    fun increaseQuantity(product: Product) {
        viewModelScope.launch {
            cartRepository.increaseQuantity(product, 1)
            refreshCart()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun decreaseQuantity(productId: Uuid) {
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
        var products = Products()

        for (page in 0.._uiState.value.currentPageIndex) {
            products +=
                productRepository.getPagingProducts(
                    page = page,
                    pageSize = pageSize,
                )
        }

        _uiState.update {
            it.copy(
                products = products,
            )
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
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
                recentlyViewedProductRepository = recentlyViewedProductRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
