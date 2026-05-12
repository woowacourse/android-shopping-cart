package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.LastViewedProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val lastViewedProductRepository: LastViewedProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProductDetailUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun increaseQuantity() {
        _uiState.update {
            it.copy(quantity = it.quantity + 1)
        }
    }

    fun decreaseQuantity() {
        _uiState.update {
            if (it.quantity == 1) {
                it
            } else {
                it.copy(quantity = it.quantity - 1)
            }
        }
    }

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            val product = productRepository.findProductById(productId) ?: return@launch
            cartRepository.increaseQuantity(
                productId = product.productId,
                quantity = _uiState.value.quantity,
            )
            _uiEvent.send(ProductDetailUiEvent.ShowMessage("장바구니에 상품을 담았습니다"))
        }
    }

    fun viewProduct(
        productId: Int,
        shouldShowLastViewedProduct: Boolean,
    ) {
        viewModelScope.launch {
            val currentProduct = productRepository.findProductById(productId) ?: return@launch
            val previousProduct = lastViewedProductRepository.getLastViewedProduct()

            _uiState.update {
                it.copy(
                    lastViewedProduct =
                        if (shouldShowLastViewedProduct) {
                            previousProduct?.takeIf { previous ->
                                previous.productId != currentProduct.productId
                            }
                        } else {
                            null
                        },
                )
            }

            recentlyViewedProductRepository.saveViewedProduct(currentProduct)
            lastViewedProductRepository.saveLastViewedProduct(currentProduct)
        }
    }
}

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val lastViewedProductRepository: LastViewedProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
                recentlyViewedProductRepository = recentlyViewedProductRepository,
                lastViewedProductRepository = lastViewedProductRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
