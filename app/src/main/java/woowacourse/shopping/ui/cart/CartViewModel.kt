package woowacourse.shopping.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.model.Cart
import woowacourse.shopping.ui.model.mapper.toUiModel

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CartUiState())
    private var page = 0
    private var cart = Cart()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCart().collect {
                cart = it
                updateUiState()
            }
        }
    }

    private fun updateUiState() {
        val cartPage = cart.getPage(page = page, pageSize = 5)
        page = cartPage.page
        uiState =
            uiState.copy(
                items = cartPage.items.map { it.toUiModel() },
                page = cartPage.page,
                isCanMoveNext = cartPage.isCanMoveNext,
                totalCartSize = cart.getTotalSize(),
                totalPrice = cart.calculateTotalPrice(),
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
}