package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.remote.NetworkMonitor
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.ui.common.model.ProductUiModel
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

        cartRepo.observeQuantityMap()
            .onEach { quantityMap ->
                _uiState.update { state ->
                    state.copy(
                        visibleProducts = state.visibleProducts.map { uiModel ->
                            uiModel.copy(quantity = quantityMap[uiModel.product.id] ?: 0)
                        },
                        cartCount = quantityMap.values.sum()
                    )
                }
            }
            .launchIn(viewModelScope)

        recentProductRepo.observeRecent()
            .onEach { products ->
                _uiState.update { it.copy(recentProducts = Products(products)) }
            }
            .launchIn(viewModelScope)
    }

    fun increase(product: Product) {
        val currentQuantity = currentQuantityOf(product.id) ?: return
        val newQuantity = currentQuantity + 1

        updateQuantity(product, currentQuantity, newQuantity)

        viewModelScope.launch {
            try {
                cartRepo.setQuantity(product, newQuantity)
            } catch (_: Exception) {
                rollBack(product, currentQuantity)
            }
        }
    }


    fun decrease(product: Product) {
        val currentQuantity = currentQuantityOf(product.id) ?: return
        if (currentQuantity <= 0) return

        val newQuantity = currentQuantity - 1

        updateQuantity(product, currentQuantity, newQuantity)

        viewModelScope.launch {
            try {
                if (newQuantity <= 0) cartRepo.delete(product)
                else cartRepo.setQuantity(product, newQuantity)
            } catch (_: Exception) {
                rollBack(product, currentQuantity)
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

    private fun currentQuantityOf(productId: UUID): Int? =
        _uiState.value.visibleProducts
            .find { it.product.id == productId }
            ?.quantity

    private fun updateQuantity(product: Product, oldQuantity: Int, newQuantity: Int) {
        _uiState.update { state ->
            state.copy(
                visibleProducts = state.visibleProducts.map { uiModel ->
                    if (uiModel.product.id == product.id) uiModel.copy(quantity = newQuantity)
                    else uiModel
                },
                cartCount = state.cartCount + (newQuantity - oldQuantity)
            )
        }
    }

    private fun rollBack(product: Product, originalQuantity: Int) {
        val current = currentQuantityOf(product.id) ?: return
        updateQuantity(product, current, originalQuantity)
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
                quantity = cartQuantityMap[product.id] ?: 0,
            )
        }
    }
}
