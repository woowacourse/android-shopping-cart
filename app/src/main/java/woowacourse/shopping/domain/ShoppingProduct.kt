package woowacourse.shopping.domain

data class ShoppingProduct(
    val productId: Long,
    var quantity: Int?,
) {
    fun add(): ShoppingProduct = copy(quantity = quantity?.plus(1))

    fun remove(): ShoppingProduct = copy(quantity = quantity?.minus(1))
}
