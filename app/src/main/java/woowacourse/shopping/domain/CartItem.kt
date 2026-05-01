package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class CartItem(
    val product: Product,
    private val count: Int,
) {
    fun increaseQuantity(): CartItem = copy(count = count + 1)

    fun decreaseQuantity(): CartItem =
        if (count == 0) {
            this
        } else {
            copy(count = count - 1)
        }

    val productId: Uuid
        get() = product.productId

    val imageUrl: String
        get() = product.imageUrl

    val productName: String
        get() = product.productName

    val price: Price
        get() = product.price

    fun count(): Int = count

    fun totalPrice(): Int = (product.price.value * count())
}
