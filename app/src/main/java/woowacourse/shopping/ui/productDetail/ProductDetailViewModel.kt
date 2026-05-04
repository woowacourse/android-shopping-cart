package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
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
                    _uiState.value = if (product != null) {
                        ProductDetailUiState.Success(product)
                    } else {
                        ProductDetailUiState.Error(
                            NoSuchElementException("상품을 찾을 수 없습니다. id=$productId")
                        )
                    }
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

    companion object {

        fun factory(
            productId: String,
            productRepository: ProductRepository,
            cartRepository: CartRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ProductDetailViewModel(
                        productId = productId,
                        productRepository = productRepository,
                        cartRepository = cartRepository,
                    )
                }
            }
    }
}
