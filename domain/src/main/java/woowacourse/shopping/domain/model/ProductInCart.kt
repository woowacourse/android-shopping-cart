package woowacourse.shopping.domain.model

data class ProductInCart(
    val product: Product,
    val quantity: Int,
    val isChecked: Boolean,
) {
    fun getTotalPriceOfProduct(): Int = quantity * product.price
}
