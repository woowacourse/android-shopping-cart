package woowacourse.shopping.domain.model

data class Cart(
    val goods: Goods,
    var quantity: Int,
) {
    val totalPrice: Int get() = goods.price * quantity
}
