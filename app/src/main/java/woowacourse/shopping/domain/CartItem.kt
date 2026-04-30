package woowacourse.shopping.domain

class CartItem(val product: Product, private val quantity: Quantity) {
    fun isSame(other: Product): Boolean = product.isSame(other)

    fun isSameCartItem(other: CartItem): Boolean = product.isSame(other.product)

    fun isSameId(other: String): Boolean = product.isSameId(other)
}
