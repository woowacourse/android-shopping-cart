package woowacourse.shopping.domain.model

data class Cart(
    val goods: Goods,
    val quantity: Int,
) {
    val totalPrice: Int = goods.price * quantity

    fun updateQuantity(newQuantity: Int): Cart = this.copy(quantity = newQuantity)
}
