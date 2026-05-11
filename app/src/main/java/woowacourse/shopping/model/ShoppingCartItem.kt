package woowacourse.shopping.model

data class ShoppingCartItem(
    val quantity: Quantity,
    val product: Product,
) {
    fun increaseQuantity(amount: Int): ShoppingCartItem {
        if (amount <= 0) return this
        try {
            val newQuantity = Quantity(quantity.value + amount)
            return copy(quantity = newQuantity)
        } catch (_: IllegalArgumentException) {
            return this
        }
    }

    fun decreaseQuantity(amount: Int): ShoppingCartItem? {
        if (amount <= 0) return this
        try {
            val newQuantity = Quantity(quantity.value - amount)
            return copy(quantity = newQuantity)
        } catch (_: IllegalArgumentException) {
            return null
        }
    }

    fun isEmpty(): Boolean = quantity.value == 0
}
