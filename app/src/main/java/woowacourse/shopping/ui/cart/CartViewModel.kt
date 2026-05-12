package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.CartResult
import woowacourse.shopping.model.Cart
import woowacourse.shopping.ui.model.mapper.toUiModel

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()
    private var page = 0
    private var cart = Cart()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCart().collect { result ->
                when (result) {
                    is CartResult.Success -> {
                        cart = result.cart
                        updateUiState()
                    }

                    is CartResult.Failure -> {
                        _uiState.value =
                            _uiState.value.copy(
                                errorMessage = "장바구니 상품 정보를 불러오지 못했습니다.",
                            )
                    }
                }
            }
        }
    }

    private fun updateUiState() {
        val cartPage = cart.getPage(page = page, pageSize = 5)
        page = cartPage.page
        _uiState.value =
            _uiState.value.copy(
                items = cartPage.items.map { it.toUiModel() }.toImmutableList(),
                page = cartPage.page,
                isCanMoveNext = cartPage.isCanMoveNext,
                totalCartSize = cart.getTotalSize(),
                totalPrice = cart.calculateTotalPrice(),
                errorMessage = null,
            )
    }

    fun nextPage() {
        page++
        updateUiState()
    }

    fun previousPage() {
        page--
        updateUiState()
    }

    fun deleteItem(productId: String) {
        viewModelScope.launch {
            cartRepository.deleteItem(productId)
        }
    }

    fun increaseQuantity(productId: String) {
        viewModelScope.launch {
            cartRepository.increaseQuantity(productId)
        }
    }

    fun decreaseQuantity(productId: String) {
        viewModelScope.launch {
            cartRepository.decreaseQuantity(productId)
        }
    }

    companion object {
        fun provideFactory(cartRepository: CartRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    CartViewModel(
                        cartRepository = cartRepository,
                    )
                }
            }
    }
}
