package woowacourse.shopping.model

class ShoppingCartItem(
    private val id: Long,
    val product: Product,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is ShoppingCartItem) return false
        return id == other.id
    }
}
