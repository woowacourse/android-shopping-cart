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

    override fun remove(shoppingCartItem: ShoppingCartItem) {
        if (!items.contains(shoppingCartItem)) {
            throw IllegalArgumentException("장바구니에 존재하지 않는 상품입니다")
        }
        items.remove(shoppingCartItem)
    }

    override fun getShoppingItems(): List<ShoppingCartItem> = items.toList()
}
