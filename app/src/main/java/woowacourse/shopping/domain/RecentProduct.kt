package woowacourse.shopping.domain

data class RecentProduct(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val timestamp: Long,
)
