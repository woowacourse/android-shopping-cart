package woowacourse.shopping.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val accumulatedProducts = mutableListOf<Product>()
    private var canLoadMore = true
    private var isLoading = false
    private var currentCart: Cart = Cart()
    private var recentProducts: List<Product> = emptyList()

    init {
        observeCart()
        observeRecentProducts()
        loadNextPage()
    }

    fun moreProducts() {
        loadNextPage()
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            cartRepository.addProduct(product)
        }
    }

    fun increase(productId: String) {
        viewModelScope.launch {
            cartRepository.increase(productId)
        }
    }

    fun decrease(productId: String) {
        viewModelScope.launch {
            cartRepository.decrease(productId)
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.cartFlow.collect { cart ->
                currentCart = cart
                updateSuccessUiState()
            }
        }
    }

    private fun observeRecentProducts() {
        viewModelScope.launch {
            recentProductRepository.getRecentProducts().collect { products ->
                recentProducts = products
                updateSuccessUiState()
            }
        }
    }

    private fun loadNextPage() {
        if (isLoading || !canLoadMore) return

        viewModelScope.launch {
            isLoading = true
            setLoadingMore(true)
            runCatching { productRepository.getProducts(currentPage, PAGE_SIZE) }
                .onSuccess { newProducts ->
                    accumulatedProducts.addAll(newProducts)
                    currentPage++

                    canLoadMore = newProducts.size == PAGE_SIZE
                    _uiState.value =
                        createSuccessUiState(
                            canLoadMore = canLoadMore,
                            isLoadingMore = false,
                        )
                }.onFailure { throwable ->
                    _uiState.value = ProductListUiState.Error.from(throwable)
                }
            isLoading = false
        }
    }

    private fun setLoadingMore(loading: Boolean) {
        val current = _uiState.value
        _uiState.value =
            when (current) {
                is ProductListUiState.Success -> current.copy(isLoadingMore = loading)
                else -> if (loading) ProductListUiState.Loading else current
            }
    }

    private fun updateSuccessUiState() {
        if (accumulatedProducts.isEmpty()) return
        val current = _uiState.value as? ProductListUiState.Success
        _uiState.value =
            createSuccessUiState(
                canLoadMore = current?.canLoadMore ?: canLoadMore,
                isLoadingMore = current?.isLoadingMore ?: false,
            )
    }

    private fun createSuccessUiState(
        canLoadMore: Boolean,
        isLoadingMore: Boolean,
    ): ProductListUiState.Success {
        val quantities =
            accumulatedProducts.associate { product ->
                product.id to currentCart.findQuantity(product.id).value
            }

        return ProductListUiState.Success(
            products = accumulatedProducts.toList(),
            recentProducts = recentProducts,
            quantitiesByProductId = quantities,
            canLoadMore = canLoadMore,
            isLoadingMore = isLoadingMore,
            totalCartCount = currentCart.totalQuantity,
        )
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun factory(
            productRepository: ProductRepository,
            cartRepository: CartRepository,
            recentProductRepository: RecentProductRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ProductListViewModel(productRepository, cartRepository, recentProductRepository)
                }
            }
    }
}
