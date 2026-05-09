package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import java.util.UUID

class ProductDetailViewModel(
    private val productRepo: ProductRepository,
    private val cartRepo: CartRepository,
    private val productId: UUID
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

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
                val cartItems = cartRepo.getAllCartItems()
                val cartQuantityMap = cartItems.items.associate {
                    it.product.id to it.quantity
                }
                _uiState.update {
                    it.copy(
                        product = product,
                        selectedQuantity = cartQuantityMap[product.id] ?: 1
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
