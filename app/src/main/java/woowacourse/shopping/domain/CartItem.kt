package woowacourse.shopping.domain
class CartItem(private val product: Product, private val quantity: Quantity) {
    fun isSame(other: Product): Boolean = product.isSame(other)
}
