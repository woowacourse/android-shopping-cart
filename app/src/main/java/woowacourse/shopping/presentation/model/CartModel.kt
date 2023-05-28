package woowacourse.shopping.presentation.model

data class CartModel(
    val id: Long,
    val product: ProductModel,
    var checked: Boolean
)
