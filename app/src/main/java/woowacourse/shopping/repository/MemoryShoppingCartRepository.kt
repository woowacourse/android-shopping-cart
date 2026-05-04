package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

data class MemoryShoppingCartRepository(
    private val items: List<ShoppingCartItem>,
    private val nextShoppingCartItemId: Long = 0L,
) : ShoppingCartRepository {
    constructor(initialProducts: List<Product>) : this(
        items =
            initialProducts.mapIndexed { index, product ->
                ShoppingCartItem(id = index.toLong(), product = product)
            },
        nextShoppingCartItemId = initialProducts.size.toLong(),
    )

    override fun add(product: Product): ShoppingCartRepository {
        val newItem =
            ShoppingCartItem(
                id = nextShoppingCartItemId,
                product = product,
            )
        return copy(
            items = items + newItem,
            nextShoppingCartItemId = nextShoppingCartItemId + 1,
        )
    }

    override fun remove(shoppingCartItem: ShoppingCartItem): ShoppingCartRepository =
        copy(items = items.filter { it.id != shoppingCartItem.id })

    override fun getShoppingItems(): List<ShoppingCartItem> = items
}
