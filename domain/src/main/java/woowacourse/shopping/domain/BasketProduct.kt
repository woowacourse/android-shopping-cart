package woowacourse.shopping.domain

typealias DomainBasketProduct = BasketProduct

data class BasketProduct(
    val id: Int = 0,
    val product: Product,
    val selectedCount: ProductCount = ProductCount(0),
) {
    constructor(product: Product, count: Int) : this(0, product, ProductCount(count))

    fun plusCount(count: Int = 1): BasketProduct =
        copy(selectedCount = selectedCount + count)

    fun minusCount(count: Int = 1): BasketProduct =
        copy(selectedCount = selectedCount - count)

    fun plusCount(count: ProductCount): BasketProduct =
        copy(selectedCount = selectedCount + count)

    fun minusCount(count: ProductCount): BasketProduct =
        copy(selectedCount = selectedCount - count)

    fun isEmpty(): Boolean = selectedCount.isZero()
}
