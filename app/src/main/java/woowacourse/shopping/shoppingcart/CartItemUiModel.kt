package woowacourse.shopping.shoppingcart

data class CartItemUiModel(
    val id: Long,
    val name: String,
    val quantity: Int,
    val totalPrice: Int,
    val imageUrl: String,
)
