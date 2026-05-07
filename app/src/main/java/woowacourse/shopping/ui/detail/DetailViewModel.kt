package woowacourse.shopping.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.ProductRepository

import woowacourse.shopping.ui.model.mapper.toUiModel

class DetailViewModel : ViewModel() {
    val uiState = MutableStateFlow(DetailUiState())
    private var productRepository: ProductRepository? = null
    private var cartRepository: CartRepository? = null
    private var id: String? = null
    private var initialized = false

    fun initialize(
        id: String,
        productRepository: ProductRepository,
        cartRepository: CartRepository,
    ) {
        if (initialized) return

        this.initialized = true
        this.id = id
        this.productRepository = productRepository
        this.cartRepository = cartRepository

        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(id)
                uiState.value = uiState.value.copy(
                    product = product.toUiModel(),
                    quantity = cartRepository.getCartItemQuantity(id),
                    totalPrice = product.getPrice() * cartRepository.getCartItemQuantity(id)
                )
            } catch (e: IllegalArgumentException) {
                uiState.value = uiState.value.copy(
                   isNotFound = true
                )
            }
        }
    }

    fun increaseQuantity() {
        uiState.value = uiState.value.copy(
            quantity = uiState.value.quantity + 1,
        )
    }

    fun decreaseQuantity() {
        uiState.value = uiState.value.copy(
            quantity = uiState.value.quantity - 1,
        )
    }

    fun addToCart() {
        viewModelScope.launch {
            val product = productRepository.getProductById(id)
            cartRepository.addItem(product, uiState.value.quantity)
        }
    }
}