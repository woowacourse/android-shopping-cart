package woowacourse.shopping.view.cart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.CartItem

class ShoppingCart {
    private val _cartItems: MutableLiveData<List<CartItem>> = MutableLiveData(listOf())
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    fun addProducts(cartItems: List<CartItem>) {
        _cartItems.value = _cartItems.value?.plus(cartItems)
    }

    fun deleteProduct(itemId: Long) {
        _cartItems.value = _cartItems.value?.filter { it.id != itemId }
    }
}
