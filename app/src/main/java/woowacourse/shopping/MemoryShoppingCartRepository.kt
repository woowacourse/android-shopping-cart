package woowacourse.shopping

object MemoryShoppingCartRepository : ShoppingCartRepository {
    private val items: MutableList<ShoppingCartItem> = mutableListOf()
    private var nextShoppingCartItemId: Long = 0L

    override fun add(product: Product) {
        items.add(
            ShoppingCartItem(
                id = nextShoppingCartItemId++,
                product = product,
            ),
        )
    }

    override fun getShoppingItems(): List<ShoppingCartItem> = items.toList()
}
