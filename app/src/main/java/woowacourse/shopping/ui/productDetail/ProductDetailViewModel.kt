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

data class ProductDetailUiState(
    val product: Product,
)

class ProductDetailViewModel(
    val productId: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    lateinit var product: Product

    init {
        viewModelScope.launch {
            product = productRepository.getProduct(productId)
        }
    }

    private val _productDetailUiState = MutableStateFlow(ProductDetailUiState(product))
    val productDetailState: StateFlow<ProductDetailUiState> = _productDetailUiState.asStateFlow()

    fun addToCart() {
        viewModelScope.launch {
            cartRepository.addCartItem(CartItem(product = productDetailState.value.product))
        }
    }
}
