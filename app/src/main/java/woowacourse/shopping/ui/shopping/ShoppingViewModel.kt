package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import java.util.UUID

class ShoppingViewModel(
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
    private val recentProductRepo: RecentProductRepository,
    private val loadSize: Int,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState = _uiState.asStateFlow()

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
                        recentProducts = recentProducts
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
                    val updatedProducts = state.visibleProducts.map { uiModel ->
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
                    val updatedProducts = state.visibleProducts.map { uiModel ->
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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val currentProducts = _uiState.value.visibleProducts
                val newProducts = productRepo.getProducts(
                    fromIndex = _uiState.value.visibleProducts.size,
                    count = loadSize,
                )
                val newUiModels = mapToProductUiModels(newProducts)

                val combineProducts = currentProducts + newUiModels
                val hasNextPage = productRepo.hasNext(combineProducts.lastIndex)
                val totalSize = productRepo.getSize()

                _uiState.update {
                    it.copy(
                        visibleCount = minOf(it.visibleCount + loadSize, totalSize),
                        visibleProducts = combineProducts,
                        hasNext = hasNextPage,
                        sizeInRepo = totalSize
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
                    recentProducts = recentProducts
                )
            }
        }
    }

    private suspend fun mapToProductUiModels(products: List<Product>): List<ProductUiModel> {
        val cartItems = cartRepo.getAllCartItems()
        val cartQuantityMap: Map<UUID, Int> = cartItems.items.associate {
            it.product.id to it.quantity
        }
        return products.map { product ->
            ProductUiModel(
                product = product,
                cartQuantity = cartQuantityMap[product.id] ?: 0
            )
        }
    }
}
