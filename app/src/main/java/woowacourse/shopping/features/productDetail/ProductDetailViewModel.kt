package woowacourse.shopping.features.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.repository.ProductRepository

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
