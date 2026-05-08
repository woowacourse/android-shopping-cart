package woowacourse.shopping.presentation.productdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

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
        val product = productRepository.findProductById(productId) ?: return
        cartRepository.increaseQuantity(
            product = product,
            quantity = _uiState.value.quantity,
        )
    }
}

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
