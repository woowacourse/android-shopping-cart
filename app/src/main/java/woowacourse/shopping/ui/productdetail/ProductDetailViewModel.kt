package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
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
            it.copy(selectedQuantity = it.selectedQuantity + 1)
        }
    }

    fun decrease() {
        _uiState.update {
            it.copy(selectedQuantity = maxOf(1, it.selectedQuantity - 1))
        }
    }

    fun addToCart() {
        val currentState = _uiState.value
        val productToSave = currentState.product ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.add(productToSave, quantity = currentState.selectedQuantity)
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
                    recentProductRepo
                        .getLastViewedProduct()
                        ?.takeIf { !isFromBanner && it.id != productId }

                _uiState.update {
                    it.copy(
                        product = product,
                        selectedQuantity = 1,
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
