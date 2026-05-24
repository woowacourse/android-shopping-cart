package woowacourse.shopping.ui.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ProductDetailViewModel(
    private val product: Product,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val showLastViewedProduct: Boolean,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUIState(
        amount = 1,
        recentProducts = emptyList(),
    ))
    val uiState = _uiState.asStateFlow()

    init {
        if (showLastViewedProduct) {
            viewModelScope.launch {
                recentProductRepository.recentProducts.collect { recentProducts ->
                    _uiState.update {
                        it.copy(
                            recentProducts = recentProducts.filterNot { recentProduct ->
                                recentProduct.productId == product.productId
                            }
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            recentProductRepository.addRecentProduct(product)
        }
    }

    fun addProductToCart() {
        viewModelScope.launch {
            cartRepository.addProduct(product, _uiState.value.amount)
        }
        _uiState.update { it.copy(amount = 1) }
    }

    fun onIncrease() {
        _uiState.update { it.copy(amount = it.amount + 1) }
    }

    fun onDecrease() {
        if(_uiState.value.isDecreaseEnable) _uiState.update { it.copy(amount = it.amount - 1) }
    }

    companion object {
        fun provideFactory(
            product: Product,
            cartRepository: CartRepository,
            recentProductRepository: RecentProductRepository,
            showLastViewedProduct: Boolean,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(
                        product,
                        cartRepository,
                        recentProductRepository,
                        showLastViewedProduct
                    ) as T
                }
            }
        }
    }
}
