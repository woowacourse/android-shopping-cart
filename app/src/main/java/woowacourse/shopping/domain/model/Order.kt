package woowacourse.shopping.domain.model

data class Order(
    val id: Int,
    val quantity: Int,
    val product: Product,
)
