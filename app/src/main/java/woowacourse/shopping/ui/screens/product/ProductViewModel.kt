package woowacourse.shopping.ui.screens.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        getMoreProducts()
    }

    fun getMoreProducts() {
        if (_uiState.value.isLoading || !_uiState.value.hasNext) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val newProducts = productRepository.getProducts()
                val hasNext = productRepository.hasNext

                _uiState.update { it.copy(products = it.products + newProducts, hasNext = hasNext) }
            } catch (e: Exception) {
                Log.e("ProductViewModel", e.message.toString())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
