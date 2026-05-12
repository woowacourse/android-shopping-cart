package woowacourse.shopping.model

data class Cart(
    val items: List<CartItem> = emptyList(),
) {
    fun addItem(product: Product): Cart {
        require(items.none { it.product == product }) { "이미 장바구니에 있는 상품입니다." }
        val newItems = items + CartItem(product = product, quantity = 1)
        return copy(items = newItems)
    }

    fun deleteItem(id: String): Cart {
        val newItems = items.filter { it.product.id != id }
        return copy(items = newItems)
    }

    fun getTotalSize(): Int = items.size

    fun getTotalQuantity(): Int = items.sumOf { it.quantity }

    fun calculateTotalPrice(): Int {
        val totalPrice = items.sumOf { it.getTotalPrice().amount }
        return totalPrice
    }
}
