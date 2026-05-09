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

    override fun deleteItem(productId: String) {
        _items.removeIf { it.product.id == productId }
    }

    override fun updateItem(cartItem: CartItem) {
        val idx = _items.indexOfFirst { it.product.id == cartItem.product.id }

        require(idx != -1) { "카트에 존재하지 않는 상품입니다." }

        _items[idx] = cartItem
    }
}
