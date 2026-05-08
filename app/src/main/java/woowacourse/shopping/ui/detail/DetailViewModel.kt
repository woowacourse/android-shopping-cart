package woowacourse.shopping.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.ui.model.mapper.toUiModel

class DetailViewModel(
    private val id: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _event = Channel<DetailEvent>()
    val event = _event.receiveAsFlow()

    init {
        loadProduct()
    }


    private fun loadProduct() {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(id)
                val quantity = cartRepository.getCartItemQuantity(id)

                _uiState.value =
                    _uiState.value.copy(
                        product = product.toUiModel(),
                        quantity = quantity,
                        totalPrice = product.getPrice() * quantity,
                    )
            } catch (e: IllegalArgumentException) {
                _uiState.value = _uiState.value.copy(isNotFound = true)
            }
        }
    }

    fun increaseQuantity() {
        val nextQuantity = _uiState.value.quantity + 1

        _uiState.value =
            _uiState.value.copy(
                quantity = nextQuantity,
                totalPrice = _uiState.value.product.price * nextQuantity,
            )
    }

    fun decreaseQuantity() {
        val nextQuantity = (_uiState.value.quantity - 1).coerceAtLeast(1)

        _uiState.value =
            _uiState.value.copy(
                quantity = nextQuantity,
                totalPrice = _uiState.value.product.price * nextQuantity,
            )
    }

    fun addToCart() {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(id)
                cartRepository.addItem(product, _uiState.value.quantity)
                _event.send(DetailEvent.NavigateToCart)
            } catch (e: IllegalArgumentException) {
                _event.send(DetailEvent.ShowAddCartFailureMessage)
            }
        }
    }

    companion object {
        fun provideFactory(
            id: String,
            productRepository: ProductRepository,
            cartRepository: CartRepository,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DetailViewModel(
                    id = id,
                    productRepository = productRepository,
                    cartRepository = cartRepository,
                )
            }
        }
    }
}
