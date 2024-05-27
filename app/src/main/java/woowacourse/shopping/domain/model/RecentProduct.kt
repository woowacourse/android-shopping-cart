package woowacourse.shopping.domain.model

class RecentProduct(
    val id: Long = DEFAULT_RECENTLY_PRODUCT_ID,
    val productId: Long,
    val imageUrl: String,
    val productName: String,
) {
    companion object {
        const val DEFAULT_RECENTLY_PRODUCT_ID = -1L
        val defaultRecentlyProduct =
            RecentProduct(
                DEFAULT_RECENTLY_PRODUCT_ID,
                DEFAULT_RECENTLY_PRODUCT_ID,
                "",
                "",
            )
    }
}
