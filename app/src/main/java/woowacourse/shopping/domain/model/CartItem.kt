package woowacourse.shopping.domain.model

data class CartItem(
    val goods: Goods,
    val quantity: Int,
) {
    val totalPrice: Int get() = goods.price * quantity
}
