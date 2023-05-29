package woowacourse.shopping.domain

data class BasketProduct(
    val id: Int = -1,
    val count: Count = Count(0),
    val product: Product,
    var isChecked: Boolean = false
) {
    fun getTotalPrice(): Price = product.price * count

    fun compareWithProductId(other: BasketProduct): Boolean =
        this.product.id == other.product.id
}
