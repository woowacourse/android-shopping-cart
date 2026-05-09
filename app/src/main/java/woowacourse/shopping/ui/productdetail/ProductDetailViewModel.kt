package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import java.util.UUID

class ProductDetailViewModel(
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun addToCart(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.increase(product)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun findProduct(id: UUID) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                _uiState.update { it.copy(product = productRepo.findProduct(id)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
