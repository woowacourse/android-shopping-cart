package woowacourse.shopping.domain.model

data class CartItem(
    val id: Long = DEFAULT_CART_ITEM_ID,
    val product: Product,
) {
    companion object {
        const val DEFAULT_CART_ITEM_ID = -1L
    }
}
