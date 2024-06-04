package woowacourse.shopping.domain

data class Cart(
    val product: Product,
    val quantity: Int = DEFAULT_PURCHASE_COUNT,
) {
    companion object {
        const val DEFAULT_PURCHASE_COUNT = 1
    }
}
