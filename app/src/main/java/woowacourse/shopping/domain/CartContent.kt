package woowacourse.shopping.domain

class CartContent(val product: Product, private val quantity: Quantity) {
    fun isSame(other: Product): Boolean = product.isSame(other)

    fun isSameCartItem(other: CartContent): Boolean = product.isSame(other.product)

    fun isSameId(other: String): Boolean = product.isSameId(other)
}
