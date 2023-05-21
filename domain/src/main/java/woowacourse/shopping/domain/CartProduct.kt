package woowacourse.shopping.domain

typealias DomainCartProduct = CartProduct

data class CartProduct(
    val id: Int = 0,
    val product: Product,
    val selectedCount: ProductCount = ProductCount(0),
    val isChecked: Boolean,
) {
    fun plusCount(count: Int = 1): CartProduct =
        copy(selectedCount = selectedCount + count)

    fun minusCount(count: Int = 1): CartProduct =
        copy(selectedCount = selectedCount - count)

    fun select(): CartProduct =
        copy(isChecked = true)

    fun unselect(): CartProduct =
        copy(isChecked = false)
}
