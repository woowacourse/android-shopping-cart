package woowacourse.shopping.data.repository.shoppingcart

import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ext.subList
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.ShoppingCartItem

class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem> {
        val items =
            DummyShoppingCart.items
                .distinct()
                .subList(pageRequest)

        return pageRequest.toPage(items, totalSize())
    }

    override fun findAll(): List<ShoppingCartItem> {
        return DummyShoppingCart.items
    }

    override fun totalSize(): Int = DummyShoppingCart.items.size

    override fun remove(item: ShoppingCartItem) {
        DummyShoppingCart.items.remove(item)
    }

    override fun save(item: ShoppingCartItem) {
        val existingItemIndex =
            DummyShoppingCart.items.indexOfFirst { it.product.id == item.product.id }

        if (existingItemIndex != -1) {
            val existingItem = DummyShoppingCart.items[existingItemIndex]
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + item.quantity)
            DummyShoppingCart.items[existingItemIndex] = updatedItem
        } else {
            DummyShoppingCart.items.add(0, item)
        }
    }

    override fun update(item: ShoppingCartItem) {
        val existingItemIndex =
            DummyShoppingCart.items.indexOfFirst { it.product.id == item.product.id }
        if (item.quantity <= 0) return

        if (existingItemIndex != -1) {
            val existingItem = DummyShoppingCart.items[existingItemIndex]
            val updatedItem = existingItem.copy(quantity = item.quantity)
            DummyShoppingCart.items[existingItemIndex] = updatedItem
        } else {
            DummyShoppingCart.items.add(0, item)
        }
    }

    override fun totalQuantity(): Int {
        return DummyShoppingCart.items.sumOf { it.quantity }
    }
}
