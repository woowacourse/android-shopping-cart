package woowacourse.shopping.domain.model

data class RecentlyProduct(
    val productId: Long,
    val imageUrl: String,
    val name: String,
) {
    companion object {
        val defaultRecentlyProduct =
            RecentlyProduct(
                -1L,
                "",
                "",
            )
    }
}
