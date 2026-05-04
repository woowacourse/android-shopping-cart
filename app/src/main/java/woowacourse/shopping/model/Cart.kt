package woowacourse.shopping.model

data class Cart(
    val items: Map<ProductId, Int>,
)
