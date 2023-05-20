package woowacourse.shopping.domain

import woowacourse.shopping.domain.util.safeSubList

typealias DomainBasket = Basket

data class Basket(
    val basketProducts: List<BasketProduct> = emptyList(),
    val loadUnit: Int,
    val minProductSize: Int = 0,
) {

    fun add(newItem: Product, count: Int = 1): Basket =
        copy(basketProducts = basketProducts.map { item ->
            if (item.product.id == newItem.id) item.plusCount(count) else item
        })

    fun remove(product: Product): Basket = copy(basketProducts = basketProducts.map { item ->
        if (item.product.id == product.id && item.selectedCount.value > minProductSize) item.minusCount() else item
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

    fun takeBasketUpToPage(page: PageNumber): Basket = copy(
        basketProducts = basketProducts.safeSubList(0, page.sizePerPage)
    )

    fun isAllChecked(page: PageNumber): Boolean =
        basketProducts.safeSubList(0, page.sizePerPage).all { it.isChecked }

    fun select(product: Product): Basket =
        copy(basketProducts = basketProducts.map { item ->
            if (item.product.id == product.id) item.select() else item
        })

    fun unselect(product: Product): Basket =
        copy(basketProducts = basketProducts.map { item ->
            if (item.product.id == product.id) item.unselect() else item
        })

    fun getCheckedSize(page: PageNumber): Int = basketProducts
        .safeSubList(0, page.sizePerPage)
        .count { it.isChecked }


    fun selectAll(): Basket =
        copy(basketProducts = basketProducts.map { it.select() })

    fun unselectAll(): Basket =
        copy(basketProducts = basketProducts.map { it.unselect() })

    fun update(basket: Basket): Basket =
        copy(basketProducts = basket.basketProducts)

    operator fun plus(items: Basket): Basket =
        copy(basketProducts = basketProducts + items.basketProducts)

    operator fun minus(item: Product): Basket = remove(item)
}
