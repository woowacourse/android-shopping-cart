package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

class InMemoryCartRepository(
    initialItems: List<CartItem> = emptyList()
) : CartRepository {
    private val items = initialItems.toMutableList()

    override suspend fun add(item: Product) {
        val existingIndex = items.indexOfFirst { it.product.id == item.id }

        if (existingIndex != -1) {
            val existingItem = items[existingIndex]
            items[existingIndex] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            items.add(CartItem(product = item, quantity = 1))
        }
    }

    override suspend fun delete(item: Product) {
        val index = items.indexOfFirst { it.product.id == item.id }
        require(index != -1) { "장바구니에 해당 제품(${item.name})이 없습니다." }

        val cartItem = items[index]

        if (cartItem.quantity > 1) {
            items[index] = cartItem.copy(quantity = cartItem.quantity - 1)
        } else {
            items.removeAt(index)
        }
    }

    override suspend fun getPagedItems(
        fromIndex: Int,
        count: Int
    ): List<CartItem> = Cart(items).getPagedItems(fromIndex, count)

    override suspend fun getSize(): Int = items.size
}
