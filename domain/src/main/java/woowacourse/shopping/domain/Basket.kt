package woowacourse.shopping.domain

typealias DomainBasket = Basket

data class Basket(
    val basketProducts: List<BasketProduct> = emptyList(),
    val loadUnit: Int,
) {
    fun add(newItem: Product): Basket = copy(basketProducts = basketProducts.map { item ->
        if (item.product == newItem) item.plusCount() else item
    })

    fun remove(product: Product): Basket = copy(basketProducts = basketProducts.map { item ->
        if (item.product == product) item.minusCount() else item
    })

    fun canLoadMore(page: PageNumber): Boolean =
        basketProducts.size >= page.value * loadUnit

    fun takeItemsUpTo(page: PageNumber): List<BasketProduct> =
        basketProducts.take(loadUnit * page.value)

    operator fun plus(item: Product): Basket = add(item)

    operator fun plus(items: Basket): Basket =
        copy(basketProducts = basketProducts + items.basketProducts)

    operator fun minus(item: Product): Basket = remove(item)
}
