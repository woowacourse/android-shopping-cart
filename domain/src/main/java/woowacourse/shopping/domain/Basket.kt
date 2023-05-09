package woowacourse.shopping.domain

data class Basket(private val products: List<Product>) {
    fun add(product: Product): Basket =
        Basket(products + product)

    fun delete(product: Product): Basket =
        Basket(products - product)
}
