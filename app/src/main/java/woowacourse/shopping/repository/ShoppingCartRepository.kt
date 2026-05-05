package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

interface ShoppingCartRepository {
    fun add(product: Product): ShoppingCartRepository

    fun remove(shoppingCartItem: ShoppingCartItem): ShoppingCartRepository

    fun getShoppingItems(): List<ShoppingCartItem>
}
