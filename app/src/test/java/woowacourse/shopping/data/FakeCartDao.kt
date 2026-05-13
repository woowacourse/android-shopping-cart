package woowacourse.shopping.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartEntity

class FakeCartDao : CartDao() {
    private val items = mutableListOf<CartEntity>()

    override fun getAllCartItems(): Flow<List<CartEntity>> = flow { emit(items.toList()) }

    override suspend fun getCartItems(
        limit: Int,
        offset: Int,
    ): List<CartEntity> = items.drop(offset).take(limit)

    override fun getCartItemsCount(): Flow<Int> = flow { emit(items.size) }

    override suspend fun getCartItem(productId: String): CartEntity? = items.find { it.productId == productId }

    override suspend fun insert(item: CartEntity) {
        items.add(item)
    }

    override suspend fun update(item: CartEntity) {
        val index = items.indexOfFirst { it.productId == item.productId }
        if (index != -1) items[index] = item
    }

    override suspend fun deleteItem(productId: String) {
        items.removeAll { it.productId == productId }
    }
}
