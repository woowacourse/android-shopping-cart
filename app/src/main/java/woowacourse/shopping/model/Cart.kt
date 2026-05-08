package woowacourse.shopping.model

data class Cart(
    val items: List<CartItem>,
) {
    init {
        require(items.map { it.productId }.distinct().size == items.size) {
            "장바구니에는 같은 상품이 중복 저장될 수 없습니다."
        }
    }

    fun add(productId: ProductId): Cart {
        val newItems = items.toMutableList()
        val index = newItems.indexOfFirst { it.productId == productId }

        if (index == -1) {
            newItems.add(CartItem(productId, 1))
        } else {
            newItems[index] = newItems[index].increase()
        }

        return Cart(newItems)
    }

    fun delete(productId: ProductId): Cart {
        val newItems = items.toMutableList()
        val index = newItems.indexOfFirst { it.productId == productId }

        require(index != -1) { "해당 상품은 장바구니에 존재하지 않습니다." }

        val updatedItem = newItems[index].decreaseOrNull()

        if (updatedItem == null) {
            newItems.removeAt(index)
        } else {
            newItems[index] = updatedItem
        }

        return Cart(newItems)
    }

    fun count(): Int = items.size
}
