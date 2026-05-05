package woowacourse.shopping.domain

class CartItem(val product: Product, private val quantity: Quantity) {
    fun hasProduct(targetProduct: Product): Boolean = this.product == targetProduct

    fun hasProductId(targetId: String): Boolean = product.hasId(targetId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CartItem) return false

        return this.product == other.product
    }

    override fun hashCode(): Int = product.hashCode()
}
