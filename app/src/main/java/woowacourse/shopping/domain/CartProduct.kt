package woowacourse.shopping.domain

data class CartProduct(
    val product: Product,
    val count: Int,
) {
    fun price(): Int = product.price * count

    fun plusCount(): CartProduct = CartProduct(product, count + 1)

    fun minusCount(): CartProduct = CartProduct(product, count - 1)
}
