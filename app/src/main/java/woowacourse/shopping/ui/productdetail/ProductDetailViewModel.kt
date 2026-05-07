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
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

class ProductDetailViewModel(
    private val productRepository: ProductRepository = InMemoryProductRepository,
    private val cartRepository: CartRepository = InMemoryCartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: ProductId) {
        viewModelScope.launch {
            refreshProductDetail(productId)
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
        val quantity =
            cartRepository
                .getCartItemsByProductIds(setOf(productId))
                .firstOrNull()
                ?.quantity ?: 0

        _uiState.value =
            _uiState.value.copy(
                product = product,
                quantity = quantity,
                isAdding = false,
            )
    }
}
