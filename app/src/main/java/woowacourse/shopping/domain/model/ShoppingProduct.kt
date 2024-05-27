package woowacourse.shopping.domain.model

data class ShoppingProduct(
    val product: Product,
    var quantity: Int = 0,
) {
    fun increase() {
        quantity += 1
    }

    fun decrease() {
        if (quantity > 0) {
            quantity -= 1
        }
    }
}
