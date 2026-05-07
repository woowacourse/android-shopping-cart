package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ProductWithQuantity(
    val product: Product,
    val quantity: Int,
) {
    init {
        require(quantity >= 0) { "상품의 수량은 음수가 될 수 없습니다." }
    }

    fun increaseQuantity(quantityToAdd: Int): ProductWithQuantity = copy(quantity = this.quantity + quantityToAdd)

    fun decreaseQuantity(quantityToRemove: Int): ProductWithQuantity = copy(quantity = this.quantity - quantityToRemove)

    val productId: Uuid
        get() = product.productId

    val imageUrl: String
        get() = product.imageUrl

    val productName: String
        get() = product.productName

    val price: Price
        get() = product.price

    fun hasSameProduct(productWithQuantity: ProductWithQuantity): Boolean = (productId == productWithQuantity.productId)

    fun totalPrice(): Int = (product.price.value * quantity)
}
