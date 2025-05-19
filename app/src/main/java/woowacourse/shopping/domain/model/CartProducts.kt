package woowacourse.shopping.domain.model

data class CartProducts(
    val cartProducts: List<CartProduct>,
    val maxPage: Int,
) {
    companion object {
        val EMPTY_CART_PRODUCTS = CartProducts(emptyList(), 0)
    }
}
