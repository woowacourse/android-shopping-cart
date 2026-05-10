package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.product.Product

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    fun increaseQuantity(quantityToAdd: Int): CartItem = copy(quantity = quantity + quantityToAdd)

    fun decreaseQuantity(): CartItem =
        if (quantity == 0) {
            this
        } else {
            copy(quantity = quantity - 1)
        }
}
