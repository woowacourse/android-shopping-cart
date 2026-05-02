package woowacourse.shopping.ui.model

data class CartItemUiModel(
    val product: ProductUiModel,
    val quantity: Int,
    val totalPrice: Int,
)
