package woowacourse.shopping.shoppingcart

data class CartItemUiModels(
    val items: List<CartItemUiModel>,
) {
    fun updateCartItem(cartItemUiModel: CartItemUiModel): CartItemUiModels =
        items.map { if (it.id == cartItemUiModel.id) cartItemUiModel else it }
            .let(::CartItemUiModels)

    fun deleteCartItem(productId: Long): CartItemUiModels = items.filterNot { it.id == productId }.let(::CartItemUiModels)
}
