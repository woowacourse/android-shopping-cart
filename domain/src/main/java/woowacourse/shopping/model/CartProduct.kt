package woowacourse.shopping.model

data class CartProduct(
    val product: Product,
    val count: Int,
    val isChecked: Boolean,
) {
    fun getTotalPrice() = count * product.price.value
    fun plusCount(count: Int) = CartProduct(
        product = product,
        count = this.count + count,
        isChecked = isChecked,
    )

    fun subCount(count: Int) = CartProduct(
        product = product,
        count = this.count - count,
        isChecked = isChecked,
    )

    fun select() = CartProduct(
        product = product,
        count = this.count,
        isChecked = true,
    )

    fun unselect() = CartProduct(
        product = product,
        count = this.count,
        isChecked = false,
    )
}
