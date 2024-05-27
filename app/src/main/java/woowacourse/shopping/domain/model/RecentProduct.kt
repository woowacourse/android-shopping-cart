package woowacourse.shopping.domain.model

class RecentProduct(
    val productId: Long,
    val imageUrl: String,
    val productName: String,
) {
    companion object {
        private const val DEFAULT_RECENTLY_PRODUCT_ID = -1L
        val defaultRecentlyProduct =
            RecentProduct(
                DEFAULT_RECENTLY_PRODUCT_ID,
                "",
                "",
            )
    }
}
