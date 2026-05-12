package woowacourse.shopping.repository.room

import woowacourse.shopping.local.cart.CartItemDao
import woowacourse.shopping.local.cart.CartItemEntity
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.CartRepository

class RoomCartRepository(
    private val cartItemDao: CartItemDao,
) : CartRepository {
    override suspend fun add(item: ProductId) {
        val productId = item.value.toString()
        val existingItem = cartItemDao.findByProductId(productId)

        if (existingItem == null) {
            cartItemDao.upsert(
                CartItemEntity(
                    productId = productId,
                    quantity = 1,
                    createdAtMillis = System.currentTimeMillis(),
                ),
            )
            return
        }

        cartItemDao.upsert(existingItem.copy(quantity = existingItem.quantity + 1))
    }

    override suspend fun delete(item: ProductId) {
        val productId = item.value.toString()
        val existingItem =
            cartItemDao.findByProductId(productId)
                ?: throw CartItemNotFoundException(item)

        if (existingItem.quantity == 1) {
            cartItemDao.deleteByProductId(productId)
            return
        }

        cartItemDao.upsert(existingItem.copy(quantity = existingItem.quantity - 1))
    }

    override suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): List<CartItem> {
        val safeFrom = fromIndex.coerceAtLeast(0)
        val safeLimit = limit.coerceAtLeast(0)

        return cartItemDao
            .getCartItems(safeFrom, safeLimit)
            .map(CartItemEntity::toDomain)
    }

    override suspend fun getCartItemsByProductIds(productIds: Set<ProductId>): List<CartItem> {
        if (productIds.isEmpty()) return emptyList()

        return cartItemDao
            .getCartItemsByProductIds(productIds.map { it.value.toString() }.toSet())
            .map(CartItemEntity::toDomain)
    }

    override suspend fun count(): Int = cartItemDao.count()
}
