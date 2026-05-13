package woowacourse.shopping.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import woowacourse.shopping.model.ShoppingItem
import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemDao
import woowacourse.shopping.storage.room.shoppingItem.toDomain

class RoomShoppingItemRepository(
    private val shoppingItemDao: ShoppingItemDao,
    scope: CoroutineScope,
) : ShoppingItemRepository {
    override val shoppingItems: StateFlow<List<ShoppingItem>> =
        shoppingItemDao
            .observeAll()
            .map { entities -> entities.map { entity -> entity.toDomain() } }
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    override suspend fun getQuantity(productId: Long): Int =
        shoppingItemDao.getQuantityOrNull(productId)
            ?: throw IllegalArgumentException("해당 상품을 찾을 수 없습니다.")

    override suspend fun plusQuantity(
        productId: Long,
        amount: Int,
    ) {
        if (amount == 0) return
        updateQuantityByDelta(productId, amount)
    }

    override suspend fun minusQuantity(
        productId: Long,
        amount: Int,
    ) {
        if (amount == 0) return
        updateQuantityByDelta(productId, -amount)
    }

    private suspend fun updateQuantityByDelta(
        productId: Long,
        delta: Int,
    ) {
        val currentQuantity =
            shoppingItemDao.getQuantityOrNull(productId)
                ?: throw IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        val updatedQuantity = currentQuantity + delta
        if (updatedQuantity < 0) {
            throw IllegalArgumentException("상품의 수량은 0보다 작을 수 없습니다.")
        }
        shoppingItemDao.updateQuantity(productId, updatedQuantity)
    }
}
