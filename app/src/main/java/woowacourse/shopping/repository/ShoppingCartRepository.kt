package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

interface ShoppingCartRepository {
    fun add(product: Product)

    fun remove(shoppingCartItem: ShoppingCartItem): ShoppingCartItem?

    fun getShoppingItems(): List<ShoppingCartItem>
}
