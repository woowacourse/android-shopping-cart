package woowacourse.shopping.data.model

typealias DataBasketProduct = BasketProduct

class BasketProduct(
    val id: Int,
    val count: DataCount,
    val product: DataProduct
)
