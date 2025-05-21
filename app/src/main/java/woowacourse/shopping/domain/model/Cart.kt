package woowacourse.shopping.domain.model

data class Cart(
    val goods: Goods,
    var quantity: Int = 0,
)
