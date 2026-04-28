package woowacourse.shopping

import kotlinx.coroutines.flow.StateFlow

interface ShoppingCartRepository {
    fun add(product: Product)
    fun remove(shoppingCartItem: ShoppingCartItem)
    fun getShoppingItems(): StateFlow<List<ShoppingCartItem>>
}
