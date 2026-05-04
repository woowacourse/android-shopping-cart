package woowacourse.shopping.domain

class CartContent(val product: Product, private val cartContentQuantity: CartContentQuantity) {
    val productId: String get() = product.id
    fun hasProductId(id: String): Boolean = productId == id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartContent

        if (product != other.product) return false
        if (cartContentQuantity != other.cartContentQuantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = product.hashCode()
        result = 31 * result + cartContentQuantity.hashCode()
        return result
    }
}
