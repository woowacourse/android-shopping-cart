package woowacourse.shopping.domain.model

data class ShoppingCartItem(
    val goods: Goods,
    val quantity: Int = DEFAULT_QUANTITY,
) {
    val price: Int
        get() = goods.price.value * quantity

    fun increaseQuantity(): ShoppingCartItem = copy(quantity = quantity + 1)

    fun decreaseQuantity(): ShoppingCartItem {
        return if (quantity > 0) {
            copy(quantity = quantity - 1)
        } else {
            this
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY = 0
    }
}