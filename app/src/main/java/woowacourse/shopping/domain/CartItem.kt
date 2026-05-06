package woowacourse.shopping.domain

data class CartItem(
    val product: Product,
    val quantity: Quantity,
) {
    fun hasProduct(targetProduct: Product): Boolean = this.product == targetProduct

    fun hasProductId(targetId: String): Boolean = product.hasId(targetId)

    fun increase(quantity: Quantity): CartItem = this.copy(quantity = this.quantity + quantity)

    fun decrease(quantity: Quantity): CartItem = this.copy(quantity = this.quantity - quantity)
}
