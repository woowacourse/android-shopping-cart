package woowacourse.shopping.domain

typealias DomainBasket = Basket

data class Basket(
    val basketProducts: List<BasketProduct> = emptyList(),
    val loadUnit: Int ,
) {
    val productsCountInBasket: Int =
        basketProducts.sumOf { product -> product.selectedCount.value }

    fun add(newItem: Product): Basket = copy(basketProducts = basketProducts.map { item ->
        if (item.product == newItem) item.plusCount() else item
    })

    fun remove(product: Product): Basket = copy(basketProducts = basketProducts.map { item ->
        if (item.product == product) item.minusCount() else item
    })

    fun canLoadMore(): Boolean =
        basketProducts.size >= loadUnit && (basketProducts.size % loadUnit >= 1 || loadUnit == 1 && basketProducts.size > loadUnit)

    fun getItems(): List<BasketProduct> = basketProducts.toList()

    fun getItemsByUnit(): List<BasketProduct> = basketProducts.take(
        (basketProducts.size / loadUnit).coerceAtLeast(1) * loadUnit
    )

    operator fun plus(item: Product): Basket = add(item)

    operator fun plus(items: Basket): Basket = copy(basketProducts = basketProducts + items.basketProducts)

    operator fun minus(item: Product): Basket = remove(item)


}
