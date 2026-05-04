package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

class MemoryShoppingCartRepository : ShoppingCartRepository {
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

    override fun remove(shoppingCartItem: ShoppingCartItem): ShoppingCartItem? =
        if (items.remove(shoppingCartItem)) shoppingCartItem else null

    override fun getShoppingItems(): List<ShoppingCartItem> = items.toList()
}
