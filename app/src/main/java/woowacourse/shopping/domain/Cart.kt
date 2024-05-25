package woowacourse.shopping.domain

data class Cart(
    val productId: Long,
    val quantity: Int = DEFAULT_PURCHASE_COUNT,
) {
    companion object {
        const val DEFAULT_PURCHASE_COUNT = 1
    }
}
