package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.ui.cart.state.CartUiState

class CartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    private var cart = Cart()

    fun incrementQuantity(productId: String) {
    }

    fun decrementQuantity(productId: String) {
    }

    fun deleteCartItem(productId: String) {
    }

    fun onLeftClick() {
    }

    fun onRightClick() {
    }
}
