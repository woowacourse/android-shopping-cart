package woowacourse.shopping.feature.productdetail.viewmodel

import androidx.lifecycle.ViewModel
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
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.data.repository.product.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.feature.common.state.ProductUiModel

data class ProductDetailUiState(
    val productState: ProductDetailLoadingState = ProductDetailLoadingState.Loading,
    val recentProductState: ProductDetailLoadingState = ProductDetailLoadingState.None,
    val quantity: Int = 1,
)

sealed interface ProductDetailLoadingState {
    data object None : ProductDetailLoadingState
    data object Loading : ProductDetailLoadingState
    data class Success(
        val product: ProductUiModel,
    ) : ProductDetailLoadingState

    data class Error(
        val errorString: String,
    ) : ProductDetailLoadingState
}

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun initialLoading(
        productId: String,
        recentProductId: String? = null,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(productState = ProductDetailLoadingState.Loading) }
            val productLoadingState = getProduct(productId)
            if (recentProductId != null && productId != recentProductId) {
                val recentProductLoadingState = getProduct(recentProductId)
                _uiState.update {
                    it.copy(
                        productState = productLoadingState,
                        recentProductState = recentProductLoadingState,
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        productState = productLoadingState,
                    )
                }
            }
        }
    }

    suspend fun getProduct(productId: String): ProductDetailLoadingState = runCatching { productRepository.getProduct(productId) }
        .fold(
            onSuccess = { ProductDetailLoadingState.Success(toProductUiModel(it)) },
            onFailure = {
                ProductDetailLoadingState.Error(
                    it.message
                        ?: "unknown error",
                )
            },
        )

    fun toProductUiModel(product: Product): ProductUiModel {
        return ProductUiModel(
            name = product.name,
            price = product.priceAmount(),
            imageUrl = product.imageUrl,
            id = product.id,
            quantity = 0,
        )
    }

    fun increase() {
        _uiState.update {
            it.copy(quantity = it.quantity + 1)
        }
    }

    fun decrease() {
        _uiState.update {
            it.copy(quantity = it.quantity - 1)
        }
    }

    fun addToCart() {
        val loadingState = uiState.value.productState as? ProductDetailLoadingState.Success
            ?: return
        viewModelScope.launch {
            val cart = cartRepository.loadCart()
            val previousQuantity = cart.quantityOf(
                productId = loadingState.product.id,
            )
            cartRepository.setProductQuantity(loadingState.product.id, previousQuantity + uiState.value.quantity)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                ProductDetailViewModel(app.productRepository, app.cartRepository)
            }
        }
    }
}
