package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MemoryShoppingCartRepository(
    initinalProducts: List<Product>,
) : ShoppingCartRepository {
    private val items: MutableList<ShoppingCartItem> =
        initinalProducts
            .map { product ->
                ShoppingCartItem(id = Uuid.random().toString(), Quantity(0), product = product)
            }.toMutableList()

    private fun getShoppingCartItemId(): String = Uuid.random().toString()

    override fun add(product: Product) {
        items.add(
            ShoppingCartItem(
                id = getShoppingCartItemId(),
                quantity = Quantity(0),
                product = product,
            ),
        )
    }

    override fun remove(shoppingCartItem: ShoppingCartItem) {
        items.remove(shoppingCartItem)
    }

    override fun getShoppingItems(): List<ShoppingCartItem> = items.toList()
}
