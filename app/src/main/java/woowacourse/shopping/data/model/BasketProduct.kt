package woowacourse.shopping.data.model

typealias DataBasketProduct = BasketProduct

data class BasketProduct(
    val id: Int,
    val product: DataProduct,
    val selectedCount: DataProductCount = DataProductCount(0),
)
