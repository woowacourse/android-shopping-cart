package woowacourse.shopping.model.products

data class CartState(
    val items: Map<Int, ShoppingCartItem> = emptyMap(),
) {
    fun getQuantity(productId: Int): Int = items[productId]?.quantity ?: 0

    fun getAllShoppingCartItem(): List<ShoppingCartItem> = items.values.toList()
}
