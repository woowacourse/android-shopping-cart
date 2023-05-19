package woowacourse.shopping.domain

import woowacourse.shopping.domain.util.safeSubList

typealias DomainBasket = Basket

data class Basket(
    val basketProducts: List<BasketProduct> = emptyList(),
    val loadUnit: Int,
) {
    fun add(newItem: Product, count: Int = 1): Basket =
        copy(basketProducts = basketProducts.map { item ->
            if (item.product.id == newItem.id) item.plusCount(count) else item
        })

    fun remove(product: Product): Basket = copy(basketProducts = basketProducts.map { item ->
        if (item.product.id == product.id) item.minusCount() else item
    })

    /* Shopping */
    fun canLoadMore(page: PageNumber): Boolean =
        basketProducts.size >= page.value * loadUnit

    fun takeItemsUpTo(page: PageNumber): List<BasketProduct> =
        basketProducts.take(loadUnit * page.value)

    /* Basket */
    fun canLoadNextPage(page: PageNumber): Boolean =
        basketProducts.size > page.sizePerPage

    fun takeItemsUpToPage(page: PageNumber): List<BasketProduct> =
        basketProducts.safeSubList(0, page.sizePerPage)

    fun getTotalPrice(page: PageNumber): Int = basketProducts
        .safeSubList(0, page.sizePerPage)
        .sumOf { it.product.price.value * it.selectedCount.value }

    operator fun plus(items: Basket): Basket =
        copy(basketProducts = basketProducts + items.basketProducts)

    operator fun minus(item: Product): Basket = remove(item)
}
