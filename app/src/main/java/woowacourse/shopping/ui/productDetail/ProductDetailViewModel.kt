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
    val product: Product? = null,
)

class ProductDetailViewModel(
    val productId: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _productDetailState = MutableStateFlow(ProductDetailUiState())
    val productDetailState: StateFlow<ProductDetailUiState> = _productDetailState.asStateFlow()


    init {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId)
            _productDetailState.value = ProductDetailUiState(product = product)
        }
    }

    suspend fun addToCart() {
        cartRepository.addCartItem(CartItem(product = productDetailState.value.product!!))
    }
}
