package woowacourse.shopping.domain

data class BasketProduct(
    val id: Int = -1,
    val count: Count = Count(0),
    val product: Product
) {
    fun getTotalPrice(): Price = product.price * count
}
