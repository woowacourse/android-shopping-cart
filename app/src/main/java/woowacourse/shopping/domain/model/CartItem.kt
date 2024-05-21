package woowacourse.shopping.domain.model

data class CartItem(
    val id: Long,
    val product: Product,
) {
    companion object {
        val defaultCartItem =
            CartItem(
                -1L,
                Product.defaultProduct,
            )
    }
}
