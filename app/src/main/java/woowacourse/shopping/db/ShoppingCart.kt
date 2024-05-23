package woowacourse.shopping.db

import android.util.Log
import woowacourse.shopping.model.CartItem

object ShoppingCart {
    private val _cartItems = mutableListOf<CartItem>()
    val cartItems get() = _cartItems.toList()

    fun addProductToCart(productId: Int) {
        val item = _cartItems.find { it.productId == productId }
        if (item != null) {
            item.quantity++
        } else {
            _cartItems.add(CartItem(productId, 1))
        }
    }

    fun delete(productId: Int) {
        _cartItems.removeAll { it.productId == productId }
    }

    fun addProductCount(productId: Int) {
        _cartItems.find { it.productId == productId }?.quantity?.plus(1)
        Log.d("quantity", _cartItems.find { it.productId == productId }?.quantity.toString())
    }

    fun subtractProductCount(productId: Int) {
        val item = _cartItems.find { it.productId == productId }
        if (item != null) {
            if (item.quantity > 1) {
                item.quantity--
            } else {
                _cartItems.remove(item)
            }
        }
    }
}
