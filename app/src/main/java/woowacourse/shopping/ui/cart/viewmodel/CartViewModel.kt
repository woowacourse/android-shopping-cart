package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.cart.state.CartUiState
import woowacourse.shopping.ui.model.DetailProductUiModel

class CartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    private var cart = Cart()

    init {
        MockData.MOCK_PRODUCTS.take(12).forEach { product ->
            cart = cart.plusProduct(product, Quantity(1))
        }
        syncUiState()
    }

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

    private fun syncUiState() {
        _uiState.update { state ->
            state.copy(
                cartItems = getPaginationItems().map { item ->
                    item.toUiModel()
                },
                totalPage = calTotalPage(),
            )
        }
    }

    private fun getPaginationItems(): List<CartItem> {
        if (cart.cartItems.isEmpty()) return emptyList()

        val fromIndex = (_uiState.value.page - 1) * PAGE_SIZE

        if (fromIndex >= cart.cartItems.size) return emptyList()

        val toIndex = minOf(fromIndex + PAGE_SIZE, cart.cartItems.size)
        return cart.cartItems.subList(fromIndex, toIndex)
    }

    private fun calTotalPage(): Int {
        if (cart.cartItems.isEmpty()) return 1
        return (cart.cartItems.size + PAGE_SIZE - 1) / PAGE_SIZE
    }

    private fun CartItem.toUiModel(): DetailProductUiModel = DetailProductUiModel.of(
        name = product.name,
        price = totalPrice.amount,
        imageUrl = product.imageUrl,
        id = product.id,
        quantity = quantity.count,
    )

    companion object {
        private const val PAGE_SIZE = 5
    }
}
