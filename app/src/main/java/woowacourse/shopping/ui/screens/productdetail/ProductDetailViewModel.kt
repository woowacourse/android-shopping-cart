package woowacourse.shopping.ui.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ui.model.UiLastViewProduct

data class ProductDetailUiState(
    val product: Product,
    val recentProduct: UiLastViewProduct,
    val quantity: Int = 1,
)

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val targetProductId: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ProductDetailUiState(
            product = productRepository.getProductById(targetProductId),
            recentProduct = getLastViewProduct(),
        ),
    )

    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private fun getLastViewProduct(): UiLastViewProduct {
        val id = recentProductRepository.getLastViewProductId()

        return UiLastViewProduct(
            id = id,
            name = productRepository.getProductById(id).name,
        )
    }

    fun addToCart() {
        viewModelScope.launch {
            cartRepository.addItem(targetProductId, _uiState.value.quantity)
        }
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
                        recentProductRepository = app.recentProductRepository,
                        targetProductId = productId,
                    )
                }
            }
    }
}
