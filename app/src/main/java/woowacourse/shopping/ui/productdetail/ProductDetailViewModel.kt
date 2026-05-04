package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository

class ProductDetailViewModel(
    private val cartRepository: CartRepository = InMemoryCartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun setProduct(product: Product) {
        if (_uiState.value.product != null) return
        _uiState.value = _uiState.value.copy(product = product)
    }

    fun addToCart() {
        val product = _uiState.value.product ?: return
        if (_uiState.value.isAdding) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAdding = true)

            cartRepository.add(product.id)

            _uiState.value =
                _uiState.value.copy(
                    isAdding = false,
                    isAdded = true,
                )
        }
    }
}
