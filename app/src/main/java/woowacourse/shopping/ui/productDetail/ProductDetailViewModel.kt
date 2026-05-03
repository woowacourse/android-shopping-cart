package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.product.ProductRepository

class ProductDetailViewModel(
    val productId: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            runCatching { productRepository.getProduct(productId) }
                .onSuccess { product ->
                    _uiState.value = ProductDetailUiState.Success(product)
                }
                .onFailure { throwable ->
                    _uiState.value = ProductDetailUiState.Error(throwable)
                }
        }
    }

    fun addToCart() {
        val current = _uiState.value as? ProductDetailUiState.Success ?: return
        viewModelScope.launch {
            cartRepository.addCartItem(CartItem(product = current.product))
        }
    }
}
