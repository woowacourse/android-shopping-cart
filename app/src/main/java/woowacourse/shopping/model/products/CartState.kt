package woowacourse.shopping.model.products

data class CartState(
    val items: Map<String, ShoppingCartItem> = emptyMap(),
) {
    fun getQuantity(productId: String): Int = items[productId]?.quantity ?: 0

    fun getAllShoppingCartItem(): List<ShoppingCartItem> = items.values.toList()
}
