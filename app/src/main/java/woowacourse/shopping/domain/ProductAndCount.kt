package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ProductAndCount(
    val product: Product,
    private val count: Int,
) {
    init {
        require(count >= 0) { "상품의 수량은 음수가 될 수 없습니다." }
    }

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
