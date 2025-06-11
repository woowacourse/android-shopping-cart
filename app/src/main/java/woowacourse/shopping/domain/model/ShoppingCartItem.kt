package woowacourse.shopping.domain.model

import woowacourse.shopping.domain.model.goods.Goods

data class ShoppingCartItem(
    val goods: Goods,
    val quantity: Int = DEFAULT_QUANTITY,
) {
    val totalPrice: Int
        get() = goods.price.value * quantity

    fun increaseQuantity(): ShoppingCartItem = copy(quantity = quantity + 1)

    fun decreaseQuantity(): ShoppingCartItem = copy(
        quantity = (quantity - 1).coerceAtLeast(
            DEFAULT_QUANTITY
        )
    )

    companion object {
        private const val DEFAULT_QUANTITY = 0
    }
}
