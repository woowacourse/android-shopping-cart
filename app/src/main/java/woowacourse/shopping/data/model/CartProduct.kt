package woowacourse.shopping.data.model

typealias DataCartProduct = CartProduct

data class CartProduct(
    val id: Int,
    val product: DataProduct,
    val selectedCount: DataProductCount = DataProductCount(0),
    val isChecked: Int,
)
