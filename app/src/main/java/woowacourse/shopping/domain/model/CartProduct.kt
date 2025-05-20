package woowacourse.shopping.domain.model

import woowacourse.shopping.domain.model.Product.Companion.EMPTY_PRODUCT

data class CartProduct(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice: Int get() = product.price * quantity

    fun increaseQuantity(delta: Int = DEFAULT_QUANTITY_DELTA): CartProduct = copy(quantity = quantity + delta)

    fun decreaseQuantity(delta: Int = DEFAULT_QUANTITY_DELTA): CartProduct =
        if (quantity - delta >= 0) {
            copy(quantity = quantity - delta)
        } else {
            this
        }

    companion object {
        val EMPTY_CART_PRODUCT = CartProduct(EMPTY_PRODUCT, 0)
        private const val DEFAULT_QUANTITY_DELTA = 1
    }
}
