package woowacourse.shopping.domain

data class CartItem(
    val product: Product,
    val quantity: Quantity,
) {
    val totalPrice: Money = product.calPrice(quantity)

    fun hasProductId(targetId: String): Boolean = product.hasId(targetId)

    fun hasProduct(targetProduct: Product): Boolean = product == targetProduct

    fun isSameQuantity(otherQuantity: Quantity): Boolean = this.quantity == otherQuantity

    fun isQuantityLessThan(otherQuantity: Quantity): Boolean = quantity.isLessThan(otherQuantity)

    fun increase(quantity: Quantity): CartItem = this.copy(quantity = this.quantity + quantity)

    fun decrease(quantity: Quantity): CartItem = this.copy(quantity = this.quantity - quantity)
}
