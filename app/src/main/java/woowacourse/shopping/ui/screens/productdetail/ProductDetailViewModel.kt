package woowacourse.shopping.ui.screens.productdetail

import android.util.Log
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
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRecentRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.coroutines.cancellation.CancellationException

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productRecentRepository: ProductRecentRepository,
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
            val latestProduct = productRecentRepository.getLatestViewedProduct()

            _uiState.update {
                it.copy(
                    product = product,
                    latestProduct = if (product.id == latestProduct?.productId) null else latestProduct,
                )
            }
            productRecentRepository.insertRecentProduct(productId)
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

            cartRepository.addItem(targetProduct.id, uiState.value.amount)
        }

    private fun launchWithLoading(action: suspend () -> Unit) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                action()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ProductDetailViewModel", e.message.toString())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ShoppingApplication)

                ProductDetailViewModel(
                    productRepository = application.productRepository,
                    cartRepository = application.cartRepository,
                    productRecentRepository = application.productRecentRepository,
                )
            }
        }
    }
}
