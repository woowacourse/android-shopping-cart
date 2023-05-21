package woowacourse.shopping.domain

typealias DomainBasketProduct = BasketProduct

data class BasketProduct(
    val id: Int = 0,
    val product: Product,
    val selectedCount: ProductCount = ProductCount(0),
    val isChecked: Boolean,
) {
    fun plusCount(count: Int = 1): BasketProduct =
        copy(selectedCount = selectedCount + count)

    fun minusCount(count: Int = 1): BasketProduct =
        copy(selectedCount = selectedCount - count)

    fun select(): BasketProduct =
        copy(isChecked = true)

    fun unselect(): BasketProduct =
        copy(isChecked = false)
}
