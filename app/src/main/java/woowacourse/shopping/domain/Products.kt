package woowacourse.shopping.domain

data class Products(
    val product: Product,
    val count: Int,
) {
    fun price(): Int = product.price * count

    fun plusCount(): Products = Products(product, count + 1)

    fun minusCount(): Products = Products(product, count - 1)
}
