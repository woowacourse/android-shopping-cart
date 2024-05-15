package woowacourse.shopping.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ShoppingCart {
    private val _cartItems: MutableLiveData<List<CartItem>> = MutableLiveData(listOf())
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    fun addProduct(cartItem: CartItem) {
        _cartItems.value = _cartItems.value?.plus(cartItem)
    }

    fun deleteProduct(itemId: Long) {
        _cartItems.value = _cartItems.value?.filter { it.id != itemId }
    }

    companion object {
        fun makeShoppingCart(cartItems: List<CartItem>): ShoppingCart {
            return ShoppingCart().apply {
                _cartItems.postValue(cartItems)
            }
        }
    }
}
