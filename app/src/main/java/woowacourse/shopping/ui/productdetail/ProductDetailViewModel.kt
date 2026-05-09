package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import woowacourse.shopping.repository.ShoppingRepositoryProvider

class ProductDetailViewModel(
    private val productRepository: ProductRepository = ShoppingRepositoryProvider.productRepository,
    private val cartRepository: CartRepository = ShoppingRepositoryProvider.cartRepository,
    private val recentProductRepository: RecentProductRepository = ShoppingRepositoryProvider.recentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: ProductId) {
        viewModelScope.launch {
            val product = productRepository.findAllByIds(setOf(productId))[productId] ?: return@launch
            recentProductRepository.recordView(product.id)
            refreshProductDetail(product.id)
        }
    }

    fun addToCart() {
        increaseQuantity()
    }

    fun increaseQuantity() {
        val product = _uiState.value.product ?: return
        if (_uiState.value.isAdding) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAdding = true)

            cartRepository.add(product.id)
            refreshProductDetail(product.id)
        }
    }

    fun decreaseQuantity() {
        val product = _uiState.value.product ?: return
        if (_uiState.value.isAdding || _uiState.value.quantity == 0) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAdding = true)

            cartRepository.delete(product.id)
            refreshProductDetail(product.id)
        }
    }

    private suspend fun refreshProductDetail(productId: ProductId) {
        val product = productRepository.findAllByIds(setOf(productId))[productId] ?: return
        val lastViewedProductId = recentProductRepository.getLatestViewedProductExcluding(productId)?.productId
        val lastViewedProduct =
            lastViewedProductId?.let { latestId ->
                productRepository.findAllByIds(setOf(latestId))[latestId]
            }
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
            )
    }
}
