package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
data class CartItem(
    val product: Product,
    val count: Int,
) {
    fun increaseQuantity(): CartItem = copy(count = count + 1)

    fun decreaseQuantity(): CartItem =
        if (count == 0) {
            this
        } else {
            copy(count = count - 1)
        }
}
