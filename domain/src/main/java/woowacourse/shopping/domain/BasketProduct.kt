package woowacourse.shopping.domain

typealias DomainBasketProduct = BasketProduct

data class BasketProduct(
    val id: Int,
    val product: Product,
    val selectedCount: ProductCount = ProductCount(0),
) {
    fun plusCount(): BasketProduct =
        copy(selectedCount = selectedCount + 1)

    fun minusCount(): BasketProduct =
        copy(selectedCount = selectedCount - 1)

    fun isEmpty(): Boolean = selectedCount.isZero()
}
