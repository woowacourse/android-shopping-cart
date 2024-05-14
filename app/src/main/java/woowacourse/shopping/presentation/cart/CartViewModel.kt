package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _cartItems: MutableLiveData<List<CartItem>> = MutableLiveData(emptyList())
    val cartItems: LiveData<List<CartItem>>
        get() = _cartItems

    fun loadCartItems(page: Int) {
        _cartItems.value = cartRepository.fetchCartItems(page)
    }

    fun removeCartItem(
        currentPage: Int,
        cartItemId: Long,
    ) {
        cartRepository.removeCartItem(cartItemId = cartItemId)
        loadCartItems(currentPage)
    }
}
