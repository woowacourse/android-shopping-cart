package woowacourse.shopping.domain.model

data class CartItem(
    val id: Long,
    val product: Product,
) {
    companion object {
        const val DEFAULT_CART_ITEM_ID = -1L
        val defaultCartItem =
            CartItem(
                DEFAULT_CART_ITEM_ID,
                Product.defaultProduct,
            )
    }
}
