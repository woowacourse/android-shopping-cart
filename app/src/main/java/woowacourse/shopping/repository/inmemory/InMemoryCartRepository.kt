package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

object InMemoryCartRepository : CartRepository {
    private val items = mutableListOf<CartItem>()

    override suspend fun showAll() = Cart(items.toList())

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
}
