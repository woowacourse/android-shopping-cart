package woowacourse.shopping.model

class ShoppingCartItem(
    private val id: Long,
    private val shoppingItem: ShoppingItem,
) {
    fun getId(): Long = id

    val product: Product
        get() = shoppingItem.getProduct()

    fun getQuantity(): Int = shoppingItem.getQuantity()

    fun getProductQuantityPrice(): Int = shoppingItem.getProductQuantityPrice()

    override fun equals(other: Any?): Boolean {
        if (other !is ShoppingCartItem) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
