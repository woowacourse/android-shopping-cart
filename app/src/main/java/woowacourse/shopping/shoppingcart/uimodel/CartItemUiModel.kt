package woowacourse.shopping.shoppingcart.uimodel

data class CartItemUiModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val cartItemCount: Int = 1,
) {
    val totalPrice: Int = price * cartItemCount

    fun isMinCount(): Boolean = cartItemCount == 1
}
