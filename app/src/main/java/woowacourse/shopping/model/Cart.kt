package woowacourse.shopping.model

data class Cart(
    val id: Long = 0,
    val product: Product,
    val count: Int,
)
