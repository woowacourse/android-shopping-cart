package woowacourse.shopping.data.repository.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import java.util.UUID

class RoomCartRepository(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository,
) : CartRepository {
    override suspend fun getAllCartItems(): Cart {
        val cartItems = toCartItems()
        return Cart(cartItems)
    }

    override suspend fun setQuantity(
        item: Product,
        quantity: Int
    ) {
        cartDao.upsert(CartEntity(item.id, quantity))
    }

    override suspend fun delete(item: Product) {
        cartDao.delete(item.id)
    }

    override suspend fun getPagedItems(
        fromIndex: Int,
        count: Int,
    ): List<CartItem> {
        require(fromIndex >= 0) { "$fromIndex 는 0 이상의 정수여야 합니다." }
        require(count >= 0) { "count는 0 이상의 정수여야 합니다." }

        val pagedEntities = cartDao.getPagedEntities(fromIndex, count)

        return pagedEntities.mapNotNull { entity ->
            val product = productRepository.findProduct(entity.productId)

            if (product != null) {
                CartItem(product = product, quantity = entity.quantity)
            } else {
                cartDao.delete(entity.productId)
                null
            }
        }
    }

    override suspend fun getSize(): Int = cartDao.getSize()

    override suspend fun getQuantity(item: Product): Int? =
        cartDao.getQuantity(item.id)

    override fun observeQuantityMap(): Flow<Map<UUID, Int>> =
        cartDao.observeAll().map { entities ->
            entities.associate { entity ->
                Pair(entity.productId, entity.quantity)
            }
        }

    private suspend fun toCartItems(): List<CartItem> {
        val cartEntities = cartDao.getAll()

        val items =
            cartEntities.mapNotNull { entity ->
                val product = productRepository.findProduct(entity.productId)

                if (product != null) {
                    CartItem(product = product, quantity = entity.quantity)
                } else {
                    cartDao.delete(entity.productId)
                    null
                }
            }
        return items
    }
}
