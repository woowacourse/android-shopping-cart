package woowacourse.shopping.domain

class CartContent(
    val product: Product,
    val quantity: Int,
) {
    init {
        require(quantity > 0) { "수량은 1보다 작을 수 없습니다. 수량 : $quantity" }
    }

    val productId: String get() = product.id
    fun hasProductId(id: String): Boolean = productId == id
}
