package woowacourse.shopping.db

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
        val position = _cartItems.indexOfFirst { it.productId == productId }
        val item = _cartItems.find { it.productId == productId } ?: return
        val newItem = item.copy(quantity = item.quantity + 1)
        _cartItems[position] = newItem
    }

    fun subtractProductCount(productId: Int) {
        val position = _cartItems.indexOfFirst { it.productId == productId }
        val item = _cartItems.find { it.productId == productId } ?: return
        val newItem = item.copy(quantity = item.quantity - 1)
        _cartItems[position] = newItem
    }
}
