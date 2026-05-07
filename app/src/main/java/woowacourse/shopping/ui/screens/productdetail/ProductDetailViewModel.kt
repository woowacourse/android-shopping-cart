package woowacourse.shopping.ui.screens.productdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDetailViewModel(
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val cartRepository: CartRepository = CartRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: String) =
        launchWithLoading {
            if (productId == "") {
                _uiState.update { it.copy(isError = true) }
                return@launchWithLoading
            }
            val product = productRepository.getProductById(productId)
            _uiState.update { it.copy(product = product) }
        }

    fun plusAmount() {
        _uiState.update { it.copy(amount = it.amount + 1) }
    }

    fun minusAmount() {
        if (_uiState.value.amount <= 1) return

        _uiState.update { it.copy(amount = it.amount - 1) }
    }

    fun addToCart() =
        launchWithLoading {
            val targetProduct = _uiState.value.product

            if (targetProduct == null) {
                _uiState.update { it.copy(isError = true) }
                return@launchWithLoading
            }

            cartRepository.addItem(targetProduct, uiState.value.amount)
        }

    private fun launchWithLoading(action: suspend () -> Unit) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                action()
            } catch (e: Exception) {
                Log.e("ProductDetailViewModel", e.message.toString())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
