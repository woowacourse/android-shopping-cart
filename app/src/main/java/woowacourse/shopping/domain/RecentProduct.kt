package woowacourse.shopping.domain

data class RecentProduct(
    val productId: String,
    val name: String,
    val imageUrl: String,
    val viewedAt: Long,
)
