package woowacourse.shopping.domain

data class RecentProduct(
    val productId: Long,
    val name: String,
    val imgUrl: String,
    val price: Long,
    val createdAt: Long,
)
