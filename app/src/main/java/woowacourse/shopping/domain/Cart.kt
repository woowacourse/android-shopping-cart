package woowacourse.shopping.domain

class Cart(
    initialItems: List<CartItem> = emptyList(),
) {
    private val _items = initialItems.toMutableList()

    val items get() = _items.toList()

    fun add(
        product: Product,
        amount: Int,
    ) {
        val idx = _items.indexOfFirst { it.product == product }

        if (idx == -1) {
            _items.add(CartItem(product = product, quantity = amount))
            return
        }

        _items[idx] = items[idx].addQuantity(amount = amount)
    }

    fun deleteItem(product: Product) {
        _items.removeIf { it.product == product }
    }
}
