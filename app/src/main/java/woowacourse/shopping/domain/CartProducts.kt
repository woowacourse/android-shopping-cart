package woowacourse.shopping.domain

class CartProducts(
    val products: List<Product> = emptyList()
){
    fun add(product: Product) = CartProducts(products + product)
    fun remove(product: Product) = CartProducts(products - product)
}
