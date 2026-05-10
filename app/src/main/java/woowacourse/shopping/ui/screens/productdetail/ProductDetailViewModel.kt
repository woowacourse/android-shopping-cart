package woowacourse.shopping.ui.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

data class ProductDetailUiState(
    val product: Product,
    val quantity: Int = 1,
)

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val targetProductId: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ProductDetailUiState(
            product = productRepository.getProductById(targetProductId),
        ),
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun addToCart() {
        cartRepository.addItem(targetProductId, _uiState.value.quantity)
    }

    fun plusCartCount() {
        _uiState.update {
            it.copy(
                quantity = it.quantity + 1,
            )
        }
    }

    fun minusCartCount() {
        if (_uiState.value.quantity == 1) return

        _uiState.update {
            it.copy(
                quantity = it.quantity - 1,
            )
        }
    }

    companion object {
        fun factory(productId: String): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val app = this[APPLICATION_KEY] as ShoppingApplication
                    ProductDetailViewModel(
                        productRepository = app.productRepository,
                        cartRepository = app.cartRepository,
                        targetProductId = productId,
                    )
                }
            }
    }
}
