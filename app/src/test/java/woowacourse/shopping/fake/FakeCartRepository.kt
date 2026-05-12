package woowacourse.shopping.fake

import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RemoveItemResult
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepository(
    private val products: Map<Long, Product> = emptyMap(),
) : CartRepository {
    private val items = mutableMapOf<Long, Int>()

    override suspend fun getCart(): Cart {
        val cartItems =
            items.mapNotNull { (id, quantity) ->
                products[id]?.let { CartItem(it, quantity) }
            }
        return Cart(cartItems)
    }

    override suspend fun getTotalCartSize(): Int = items.values.sum()

    override suspend fun addItem(
        id: Long,
        quantity: Int,
    ): AddItemResult {
        val before = items[id]
        items[id] = (before ?: 0) + quantity
        return if (before == null) {
            AddItemResult.NewAdded(getCart())
        } else {
            AddItemResult.Incremented(getCart())
        }
    }

    override suspend fun deleteItem(id: Long): RemoveItemResult =
        if (items.remove(id) != null) {
            RemoveItemResult.Success(getCart())
        } else {
            RemoveItemResult.NotFoundItem
        }

    override suspend fun decrease(id: Long): RemoveItemResult {
        val current = items[id] ?: return RemoveItemResult.NotFoundItem
        if (current == 1) items.remove(id) else items[id] = current - 1
        return RemoveItemResult.Success(getCart())
    }

    override suspend fun getAllQuantities(): Map<Long, Int> = items.toMap()

    override suspend fun getQuantity(id: Long): Int = items[id] ?: 1
}
