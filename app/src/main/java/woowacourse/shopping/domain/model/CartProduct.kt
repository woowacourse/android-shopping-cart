package woowacourse.shopping.domain.model

data class CartProduct(
    val product: Product,
    var count: Int = 0,
)
