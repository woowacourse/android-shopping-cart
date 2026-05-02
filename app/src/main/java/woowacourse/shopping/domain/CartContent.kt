package woowacourse.shopping.domain

class CartContent(val product: Product, private val quantity: Quantity) {
    val productId: String get() = product.id
    fun hasProductId(id: String): Boolean = productId == id
}
