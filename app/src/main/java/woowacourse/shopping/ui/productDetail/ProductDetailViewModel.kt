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
    val product: Product
)

class ProductDetailViewModel(
    val product: Product,
    private val cartRepository: CartRepository
): ViewModel() {
    private val _productDetailUiState = MutableStateFlow(ProductDetailUiState(product))
    val productDetailUiState: StateFlow<ProductDetailUiState> = _productDetailUiState.asStateFlow()

    fun addToCart() {
        viewModelScope.launch {
            cartRepository.addCartItem(CartItem(product = product))
        }
    }
}
