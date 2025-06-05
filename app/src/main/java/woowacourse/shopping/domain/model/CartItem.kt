package woowacourse.shopping.domain.model

data class CartItem(
    val goods: Goods,
    var quantity: Int,
) {
    val totalPrice: Int get() = goods.price * quantity
}
