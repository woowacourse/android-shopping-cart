package woowacourse.shopping.model.products

data class CartState(
    val items: Map<String, ShoppingCartItem> = emptyMap(),
) {
    val totalItems: Int get() = items.values.sumOf { it.quantity }
    val totalPrice: Int get() = items.values.sumOf { it.product.price * it.quantity }

    val isEmpty: Boolean get() = items.isEmpty()

    fun getQuantity(productId: String): Int = items[productId]?.quantity ?: 0

    fun getAllShoppingCartItem(): List<ShoppingCartItem> = items.values.toList()
}
