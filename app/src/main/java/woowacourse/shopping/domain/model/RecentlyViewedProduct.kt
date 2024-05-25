package woowacourse.shopping.domain.model

data class RecentlyViewedProduct(
    val productId: Long,
    val name: String,
    val price: Long,
    val imageUrl: String,
    val viewedAt: Long,
)
