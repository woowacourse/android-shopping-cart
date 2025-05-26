package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartItemDao
import woowacourse.shopping.data.local.CartItemEntity
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.repository.CartItemRepository
import woowacourse.shopping.utils.toProduct

class CartItemRepositoryImpl private constructor(
    private val dao: CartItemDao,
    private val cartDao: CartDao,
) : CartItemRepository {
    override fun findCartItemById(id: Long): CartItem? {
        val cartItem = dao.findById(id)
        val product = cartDao.findById(id)?.toProduct() ?: return null
        return CartItem(cartItem.id, product, cartItem.quantity)
    }

    override fun getCartItems(): List<CartItem> {
        val cartItemEntities = dao.getAll()
        return cartItemEntities.map { createCartItemWith(it) ?: return emptyList() }
    }

    override fun getPagedCartItems(
        limit: Int,
        offset: Int
    ): List<CartItem> {
        val products = cartDao.findPagedItems(limit, offset)
        val cartItemEntities = products.map { dao.findById(it.id) }
        return cartItemEntities.map { createCartItemWith(it) ?: return emptyList() }
    }

    override fun insert(cartItem: CartItem) {
        dao.insert(CartItemEntity(cartItem.id, cartItem.quantity))
    }

    override fun delete(cartItem: CartItem) {
        dao.delete(
            CartItemEntity(
                cartItem.id,
                cartItem.quantity
            )
        )
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun update(newCartItem: CartItem) {
        dao.update(CartItemEntity(newCartItem.id, newCartItem.quantity))
    }

    override fun increaseQuantity(id: Long) {
        val cartItemEntity = dao.findById(id)
        dao.update(CartItemEntity(cartItemEntity.id, cartItemEntity.quantity + 1))
    }

    override fun decreaseQuantity(id: Long) {
        val cartItemEntity = dao.findById(id)
        if (cartItemEntity.quantity == 1) {
            dao.deleteById(id)
            cartDao.deleteById(id)
            return
        }
        dao.update(CartItemEntity(cartItemEntity.id, cartItemEntity.quantity - 1))
    }

    private fun createCartItemWith(cartItemEntity: CartItemEntity): CartItem? {
        val product = cartDao.findById(cartItemEntity.id) ?: return null
        return CartItem(
            id = cartItemEntity.id,
            product = product.toProduct(),
            quantity = cartItemEntity.quantity,
        )
    }

    companion object {
        private var INSTANCE: CartItemRepositoryImpl? = null

        fun initialize(
            dao: CartItemDao,
            cartDao: CartDao,
        ) {
            if (INSTANCE == null) {
                INSTANCE = CartItemRepositoryImpl(dao, cartDao)
            }
        }

        fun get(): CartItemRepositoryImpl {
            return INSTANCE
                ?: throw IllegalStateException("CartItemRepositoryImpl가 초기화되지 않았습니다.")
        }
    }
}
