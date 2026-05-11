package woowacourse.shopping.repository.room

import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.entity.CartEntity
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class RoomCartRepository(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository
) : CartRepository {
    override suspend fun getAllCartItems(): Cart {
        val cartItems = toCartItems()
        return Cart(cartItems)
    }

    override suspend fun add(item: Product, quantity: Int) {
        val currentEntity = cartDao.getCartItemById(item.id)
        if (currentEntity != null) {
            cartDao.updateQuantity(item.id, currentEntity.quantity + quantity)
        } else {
            cartDao.insert(CartEntity(item.id, quantity))
        }
    }

    override suspend fun increase(item: Product) {
        val currentEntity = cartDao.getCartItemById(item.id)

        if (currentEntity != null) {
            cartDao.updateQuantity(productId = item.id, currentEntity.quantity + 1)
        } else {
            cartDao.insert(CartEntity(item.id, 1))
        }
    }

    override suspend fun decrease(item: Product) {
        val currentEntity = cartDao.getCartItemById(item.id)

        if (currentEntity != null) {
            if (currentEntity.quantity > 1) {
                cartDao.updateQuantity(item.id, currentEntity.quantity - 1)
            } else {
                cartDao.deleteById(item.id)
            }
        }
    }

    override suspend fun delete(item: Product) {
        cartDao.deleteById(item.id)
    }

    override suspend fun getPagedItems(
        fromIndex: Int,
        count: Int
    ): List<CartItem> {
        val cartItems = toCartItems()
        require(count >= 0) { "count는 0 이상의 수여야 합니다." }
        require(fromIndex in 0..cartItems.size) { "$fromIndex 는 장바구니 내 전체 아이템 개수보다 많을 수 없습니다." }

        return cartItems.drop(fromIndex).take(count)
    }

    override suspend fun getSize(): Int {
        val items = toCartItems()
        return items.size
    }

    private suspend fun toCartItems(): List<CartItem> {
        val cartEntities = cartDao.getAll()

        val items = cartEntities.mapNotNull { entity ->
            val product = productRepository.findProduct(entity.productId)

            if (product != null) {
                CartItem(product = product, quantity = entity.quantity)
            } else {
                cartDao.deleteById(entity.productId)
                null
            }
        }
        return items
    }
}
