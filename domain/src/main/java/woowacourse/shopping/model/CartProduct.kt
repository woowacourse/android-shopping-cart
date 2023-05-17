package woowacourse.shopping.model

data class CartProduct(
    val product: Product,
    val count: Int,
    val isSelected: Boolean,
) {
    fun getTotalPrice() = count * product.price.value
    fun plusCount(count: Int) = CartProduct(
        product = product,
        count = this.count + count,
        isSelected = isSelected,
    )

    fun subCount(count: Int) = CartProduct(
        product = product,
        count = this.count - count,
        isSelected = isSelected,
    )

    fun select() = CartProduct(
        product = product,
        count = this.count,
        isSelected = true,
    )

    fun unselect() = CartProduct(
        product = product,
        count = this.count,
        isSelected = false,
    )
}
