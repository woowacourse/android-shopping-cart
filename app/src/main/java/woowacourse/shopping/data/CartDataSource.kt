package woowacourse.shopping.data

import woowacourse.shopping.domain.CartItem

object CartDataSource {
    private val _items: MutableList<CartItem> = mutableListOf()
    val items get() = _items.toList()

    fun add(cartItem: CartItem) {
        val idx = _items.indexOfFirst { it.product == cartItem.product }

        if (idx == -1) {
            _items.add(cartItem)
            return
        }

        _items[idx] = items[idx].addQuantity(amount = cartItem.quantity)
    }

    fun deleteItem(id: String) {
        _items.removeIf { it.product.id == id }
    }
}
