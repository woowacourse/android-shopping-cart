package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import woowacourse.shopping.ui.common.paging.Pager
import java.util.UUID

class ShoppingViewModel(
    networkMonitor: NetworkMonitor,
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
    private val recentProductRepo: RecentProductRepository,
    private val loadSize: Int,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    private val pager = Pager(loadSize)
    val uiState = _uiState.asStateFlow()
    val isNetworkConnected: StateFlow<Boolean> =
        networkMonitor.isConnected
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true,
            )

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val initialProducts = productRepo.getProducts(0, loadSize)
                val uiModels = mapToProductUiModels(initialProducts)

                val hasNextPage = productRepo.hasNext(initialProducts.lastIndex)
                val totalSize = productRepo.getSize()
                val recentProducts = recentProductRepo.getRecentProducts()

                _uiState.update {
                    it.copy(
                        visibleProducts = uiModels,
                        hasNext = hasNextPage,
                        sizeInRepo = totalSize,
                        recentProducts = recentProducts,
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun increase(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.increase(product)

                _uiState.update { state ->
                    val updatedProducts =
                        state.visibleProducts.map { uiModel ->
                            if (uiModel.product.id == product.id) {
                                uiModel.copy(cartQuantity = uiModel.cartQuantity + 1)
                            } else {
                                uiModel
                            }
                        }
                    val cartCount = state.cartCount + 1
                    state.copy(visibleProducts = updatedProducts, cartCount = cartCount)
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun decrease(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.decrease(product)

                _uiState.update { state ->
                    val updatedProducts =
                        state.visibleProducts.map { uiModel ->
                            if (uiModel.product.id == product.id) {
                                uiModel.copy(cartQuantity = maxOf(0, uiModel.cartQuantity - 1))
                            } else {
                                uiModel
                            }
                        }
                    val cartCount = maxOf(0, state.cartCount - 1)
                    state.copy(visibleProducts = updatedProducts, cartCount = cartCount)
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (!pager.canLoadMore(currentState.visibleProducts.size, currentState.sizeInRepo)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val currentSize = _uiState.value.visibleProducts.size
                val currentProducts = _uiState.value.visibleProducts
                val newProducts =
                    productRepo.getProducts(
                        fromIndex = currentSize,
                        count = loadSize,
                    )
                val newUiModels = mapToProductUiModels(newProducts)
                val combineProducts = currentProducts + newUiModels
                val totalSize = productRepo.getSize()
                val hasNext = pager.canLoadMore(combineProducts.size, totalSize)

                _uiState.update {
                    it.copy(
                        visibleCount = minOf(it.visibleCount + loadSize, totalSize),
                        visibleProducts = combineProducts,
                        hasNext = hasNext,
                        sizeInRepo = totalSize,
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun syncCartState() {
        viewModelScope.launch {
            val cartItems = cartRepo.getAllCartItems()
            val totalCartCount = cartItems.items.sumOf { it.quantity }
            val recentProducts = recentProductRepo.getRecentProducts()

            _uiState.update { state ->
                val currentProducts = state.visibleProducts.map { it.product }
                val updatedUiModels = mapToProductUiModels(currentProducts)

                state.copy(
                    visibleProducts = updatedUiModels,
                    cartCount = totalCartCount,
                    recentProducts = recentProducts,
                )
            }
        }
    }

    private suspend fun mapToProductUiModels(products: List<Product>): List<ProductUiModel> {
        val cartItems = cartRepo.getAllCartItems()
        val cartQuantityMap: Map<UUID, Int> =
            cartItems.items.associate {
                it.product.id to it.quantity
            }
        return products.map { product ->
            ProductUiModel(
                product = product,
                cartQuantity = cartQuantityMap[product.id] ?: 0,
            )
        }
    }
}
