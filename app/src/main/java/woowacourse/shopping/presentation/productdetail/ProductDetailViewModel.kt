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
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
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

    @OptIn(ExperimentalUuidApi::class)
    fun addToCart(productId: Uuid) {
        viewModelScope.launch {
            val product = productRepository.findProductById(productId) ?: return@launch
            cartRepository.increaseQuantity(
                product = product,
                quantity = _uiState.value.quantity,
            )
            _uiEvent.send(ProductDetailUiEvent.ShowMessage("장바구니에 상품을 담았습니다"))
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun viewProduct(productId: Uuid) {
        viewModelScope.launch {
            val product = productRepository.findProductById(productId) ?: return@launch
            recentlyViewedProductRepository.viewProduct(product)
        }
    }
}

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
                recentlyViewedProductRepository = recentlyViewedProductRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
