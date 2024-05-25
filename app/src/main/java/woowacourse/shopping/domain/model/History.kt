package woowacourse.shopping.domain.model

data class History(
    val id: Int,
    val product: Product,
    val timestamp: Long,
)
