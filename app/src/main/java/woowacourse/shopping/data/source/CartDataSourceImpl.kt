package woowacourse.shopping.data.source

import woowacourse.shopping.domain.CartItem

object CartDataSourceImpl : CartDataSource {
    private val _items: MutableList<CartItem> = mutableListOf()
    override val items get() = _items.toList()

    override fun add(cartItem: CartItem) {
        val idx = _items.indexOfFirst { it.product.id == cartItem.product.id }

        if (idx == -1) {
            _items.add(cartItem)
            return
        }

        _items[idx] = items[idx].addQuantity(amount = cartItem.quantity)
    }

    override fun deleteItem(id: String) {
        _items.removeIf { it.product.id == id }
    }
}
