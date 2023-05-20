package woowacourse.shopping.domain

data class BasketProduct(
    val id: Int = -1,
    val count: Count = Count(0),
    val product: Product,
    var checked: Boolean = false
) {
    fun getTotalPrice(): Price = product.price * count

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BasketProduct

        if (product != other.product) return false

        return true
    }

    override fun hashCode(): Int {
        return product.hashCode()
    }
}
