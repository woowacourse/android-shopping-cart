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
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ProductDetailViewModel(
    val productId: Int,
    private val openedFromLastViewed: Boolean,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            runCatching { productRepository.getProduct(productId) }
                .onSuccess { product ->
                    _uiState.value =
                        if (product != null) {
                            val mostRecentProduct: Product? = recentProductRepository.getMostRecentProduct()
                            val lastViewedProduct =
                                if (openedFromLastViewed || product.isSameProduct(mostRecentProduct)) {
                                    null
                                } else {
                                    mostRecentProduct
                                }
                            recentProductRepository.save(product)
                            ProductDetailUiState.Success(
                                product = product,
                                lastViewedProduct = lastViewedProduct,
                            )
                        } else {
                            ProductDetailUiState.Error(
                                NoSuchElementException("상품을 찾을 수 없습니다. id=$productId"),
                            )
                        }
                }.onFailure { throwable ->
                    _uiState.value = ProductDetailUiState.Error(throwable)
                }
        }
    }

    fun increaseSelected() {
        val current = _uiState.value as? ProductDetailUiState.Success ?: return
        _uiState.value = current.copy(selectedQuantity = current.selectedQuantity + 1)
    }

    fun decreaseSelected() {
        val current = _uiState.value as? ProductDetailUiState.Success ?: return
        if (current.selectedQuantity <= 1) return
        _uiState.value = current.copy(selectedQuantity = current.selectedQuantity - 1)
    }

    fun addToCart() {
        val current = _uiState.value as? ProductDetailUiState.Success ?: return
        viewModelScope.launch {
            cartRepository.addProduct(current.product, Quantity(current.selectedQuantity))
        }
    }

    companion object {
        fun factory(
            productId: Int,
            openedFromLastViewed: Boolean,
            productRepository: ProductRepository,
            cartRepository: CartRepository,
            recentProductRepository: RecentProductRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ProductDetailViewModel(
                        productId = productId,
                        openedFromLastViewed = openedFromLastViewed,
                        productRepository = productRepository,
                        cartRepository = cartRepository,
                        recentProductRepository = recentProductRepository,
                    )
                }
            }
    }
}
