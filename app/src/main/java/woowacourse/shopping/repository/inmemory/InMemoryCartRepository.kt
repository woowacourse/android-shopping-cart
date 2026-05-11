package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

class InMemoryCartRepository(
    cartItems: List<CartItem> = emptyList(),
) : CartRepository {
    private val value = cartItems.toMutableList()

    override suspend fun getAllCartItems(): Cart = Cart(value)

    override suspend fun add(
        item: Product,
        quantity: Int,
    ) {
        require(quantity > 0) { "장바구니에 추가하는 수량($quantity)은 1 이상의 정수여야 합니다." }
        val existingIndex = value.indexOfFirst { it.product.id == item.id }

        if (existingIndex != -1) {
            val existingItem = value[existingIndex]
            value[existingIndex] = existingItem.copy(quantity = quantity)
        } else {
            value.add(CartItem(product = item, quantity = quantity))
        }
    }

    override suspend fun increase(item: Product) {
        val existingIndex = value.indexOfFirst { it.product.id == item.id }

        if (existingIndex != -1) {
            val existingItem = value[existingIndex]
            value[existingIndex] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            value.add(CartItem(product = item, quantity = 1))
        }
    }

    override suspend fun decrease(item: Product) {
        val existingIndex = value.indexOfFirst { it.product.id == item.id }
        if (existingIndex < 0) return

        val cartItem = value[existingIndex]
        if (cartItem.quantity > 1) {
            value[existingIndex] = cartItem.copy(quantity = cartItem.quantity - 1)
        } else {
            value.removeAt(existingIndex)
        }
    }

    override suspend fun delete(item: Product) {
        val existingIndex = value.indexOfFirst { it.product.id == item.id }
        require(existingIndex != -1) { "장바구니에 해당 제품(${item.name})이 없습니다." }

        value.removeAt(existingIndex)
    }

    override suspend fun getPagedItems(
        fromIndex: Int,
        count: Int,
    ): List<CartItem> {
        require(count >= 0) { "count는 0 이상의 수여야 합니다." }
        require(fromIndex in 0..value.size) { "$fromIndex 는 장바구니 내 전체 아이템 개수보다 많을 수 없습니다." }

        return value.drop(fromIndex).take(count)
    }

    override suspend fun getSize(): Int = value.size
}
