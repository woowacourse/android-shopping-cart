package woowacourse.shopping.domain.model

data class Cart(
    private val _items: List<CartItem> = emptyList(),
) {
    val items = _items.toList()
    val size: Int get() = items.size

    val totalPrice: Money get() = Money(items.sumOf { it.getTotalPrice().amount })
}
