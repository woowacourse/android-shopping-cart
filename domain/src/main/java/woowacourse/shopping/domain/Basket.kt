package woowacourse.shopping.domain

data class Basket(val products: List<BasketProduct>) {
    fun add(product: BasketProduct): Basket =
        Basket(products + product)

    fun delete(product: BasketProduct): Basket =
        Basket(products - product)
}
