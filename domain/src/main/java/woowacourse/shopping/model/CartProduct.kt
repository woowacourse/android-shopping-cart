package woowacourse.shopping.model

data class CartProduct(
    val cartId: Long,
    val product: Product,
    val quantity: Int,
    val isChecked: Boolean,
) {
    fun getTotalPrice() = quantity * product.price.value
    operator fun plus(count: Int) = copy(quantity = quantity + count)
    operator fun minus(count: Int) = copy(quantity = quantity - count)
    fun select() = copy(isChecked = true)
    fun unselect() = copy(isChecked = false)
}
