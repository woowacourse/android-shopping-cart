package woowacourse.shopping.fixture

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.ShoppingCartItem

class FakeShoppingCartRepository(
    private val data: MutableList<ShoppingCartItem> = DummyShoppingCart.items.toMutableList(),
) : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem> {
        val items =
            data
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun remove(item: ShoppingCartItem) {
        data.remove(item)
    }

    override fun totalSize(): Int {
        return data.size
    }

    override fun findAll(): List<ShoppingCartItem> {
        return data.distinct()
    }

    override fun save(item: ShoppingCartItem) {
        data.add(item)
    }

    override fun totalQuantity(): Int {
        return data.sumOf { it.quantity }
    }

    override fun update(item: ShoppingCartItem) {
        val existingItemIndex =
            data.indexOfFirst { it.product.id == item.product.id }
        if (item.quantity <= 0) return
        if (existingItemIndex != -1) {
            val existingItem = data[existingItemIndex]
            val updatedItem = existingItem.copy(quantity = item.quantity)
            data[existingItemIndex] = updatedItem
        } else {
            data.add(0, item)
        }
    }
}
