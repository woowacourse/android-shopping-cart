package woowacourse.shopping.data.repository.inmemory

import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class InMemoryCartRepository(
    cartItems: List<CartItem> = emptyList(),
) : CartRepository {
    private val value = cartItems.toMutableList()

    override suspend fun getAllCartItems(): Cart = Cart(value)

    override suspend fun setQuantity(
        item: Product,
        quantity: Int
    ) {
        TODO("Not yet implemented")
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

    override suspend fun getQuantity(item: Product): Int? {
        TODO("Not yet implemented")
    }
}
