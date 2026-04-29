package woowacourse.shopping.core.uimodel

data class CartItemUiModel(
    val product: ProductUiModel,
    val quantity: Int,
    val totalPrice: Int,
)
