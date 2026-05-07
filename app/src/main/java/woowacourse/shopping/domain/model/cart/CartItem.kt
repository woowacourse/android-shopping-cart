package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    fun increaseQuantity(): CartItem = copy(quantity = quantity + 1)

    fun decreaseQuantity(): CartItem =
        if (quantity == 0) {
            this
        } else {
            copy(quantity = quantity - 1)
        }
}
