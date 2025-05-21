package woowacourse.shopping.domain

data class CartProduct(
    val id: Long,
    val product: Product,
    val quantity: Int = MINIMUM_PRODUCT_QUANTITY,
) {
    companion object {
        private const val MINIMUM_PRODUCT_QUANTITY = 1
    }
}
