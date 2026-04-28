package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ProductAndCount(
    val product: Product,
    private val count: Int
) {
    fun increaseQuantity(): ProductAndCount = copy(count = count + 1)

    fun decreaseQuantity(): ProductAndCount = copy(count = count - 1)

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
