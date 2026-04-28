package woowacourse.shopping

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object MemoryShoppingCartRepository : ShoppingCartRepository {
    private val items: MutableList<ShoppingCartItem> = mutableListOf()
    private val shoppingItemsState = MutableStateFlow<List<ShoppingCartItem>>(emptyList())
    private var nextShoppingCartItemId: Long = 0L

    override fun add(product: Product) {
        items.add(
            ShoppingCartItem(
                id = nextShoppingCartItemId++,
                product = product,
            ),
        )
        shoppingItemsState.value = items.toList()
    }

    override fun remove(shoppingCartItem: ShoppingCartItem) {
        if(!items.contains(shoppingCartItem)){
            throw IllegalArgumentException("장바구니에 존재하지 않는 상품입니다")
        }
        items.remove(shoppingCartItem)
        shoppingItemsState.value = items.toList()
    }

    override fun getShoppingItems(): StateFlow<List<ShoppingCartItem>> = shoppingItemsState
}
