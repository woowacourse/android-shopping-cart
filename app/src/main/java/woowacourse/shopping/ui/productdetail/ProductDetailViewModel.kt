package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import java.util.UUID

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
    private val recentProductRepo: RecentProductRepository,
    private val productId: UUID,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()
    private val isFromBanner: Boolean =
        savedStateHandle[ProductDetailActivity.EXTRA_IS_FROM_BANNER] ?: false

    init {
        loadProduct()
    }

    fun increase() {
        _uiState.update {
            it.copy(quantity = it.quantity + 1)
        }
    }

    fun decrease() {
        _uiState.update {
            it.copy(quantity = maxOf(1, it.quantity - 1))
        }
    }

    fun addToCart() {
        val product = _uiState.value.product ?: return
        val quantity = _uiState.value.quantity

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val existingQuantity = cartRepo.getQuantity(product) ?: 0
                cartRepo.setQuantity(product, existingQuantity + quantity)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val product = productRepo.findProduct(productId)
                val bannerProduct =
                    recentProductRepo.getLastViewedProduct()
                        ?.takeIf { !isFromBanner && it.id != productId }

                _uiState.update {
                    it.copy(
                        product = product,
                        quantity = 1,
                        lastViewedProduct = bannerProduct,
                    )
                }
                recentProductRepo.add(productId)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
