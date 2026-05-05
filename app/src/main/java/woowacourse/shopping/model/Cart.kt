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

    fun getPage(
        page: Int,
        pageSize: Int,
    ): CartPage {
        val lastPage =
            if (items.isEmpty()) {
                0
            } else {
                items.lastIndex / pageSize
            }
        val currentPage = page.coerceIn(0, lastPage)
        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, items.size)

        return CartPage(
            items = items.subList(fromIndex, toIndex),
            page = currentPage,
            isCanMoveNext = toIndex < items.size,
        )
    }

    fun getTotalSize(): Int = items.size

    fun calculateTotalPrice(): Money {
        val totalPrice = items.sumOf { it.getTotalPrice().amount }
        return Money(totalPrice)
    }
}

data class CartPage(
    val items: List<CartItem>,
    val page: Int,
    val isCanMoveNext: Boolean,
)
