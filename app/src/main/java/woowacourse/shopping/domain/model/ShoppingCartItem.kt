package woowacourse.shopping.domain.model

data class ShoppingCartItem(
    val goods: Goods,
    val quantity: Int = DEFAULT_QUANTITY,
) {
    val price: Int
        get() = goods.price.value * quantity

    companion object {
        private const val DEFAULT_QUANTITY = 0
    }
}