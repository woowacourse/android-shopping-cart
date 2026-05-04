package woowacourse.shopping.domain.model

data class Cart(
   private val _items: List<CartItem> = emptyList(),
) {
    val items = _items.toList()
    val size = items.size

    fun addItem(product: Product): AddItemResult {
        val existItem = items.firstOrNull { it.product == product }
        return if (existItem == null) {
            AddItemResult.NewAdded(
                copy(
                    _items =
                        items +
                            CartItem(
                                product = product,
                                quantity = 1,
                            ),
                ),
            )
        } else {
            AddItemResult.DuplicateItem
        }
    }

    fun deleteItem(id: String): RemoveItemResult {
        val item = items.firstOrNull { it.product.id == id }
        return if (item == null) {
            RemoveItemResult.NotFoundItem
        } else {
            RemoveItemResult.Success(
                copy(
                    _items = items.filter { it.product.id != id },
                ),
            )
        }
    }

    fun calculateTotalPrice(): Money {
        val totalPrice = items.sumOf { it.getTotalPrice().amount }
        return Money(totalPrice)
    }
}
