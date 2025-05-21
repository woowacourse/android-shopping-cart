package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity

class CartDataSourceImpl(
    private val cartDao: CartDao,
) : CartDataSource {
    override fun getAll(): List<CartEntity> = cartDao.getAll()

    override fun getCartItemCount(): Int = cartDao.getCartItemCount()

    override fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
    ) {
        val entity = CartEntity(productId, increaseQuantity)
        cartDao.insertOrUpdate(entity, increaseQuantity)
    }

    override fun decreaseCartItemQuantity(productId: Long) {
        cartDao.decreaseOrDelete(productId)
    }

    override fun deleteCartItem(productId: Long) {
        cartDao.delete(productId)
    }

    override fun loadCartItems(
        offset: Int,
        limit: Int,
    ): List<CartEntity> = cartDao.getCartItemPaged(limit, offset)

    override fun findQuantityByProductId(productId: Long): Int = cartDao.findQuantityByProductId(productId)

    override fun existsItemCreatedAfter(createdAt: Long): Boolean = cartDao.existsItemCreatedAfter(createdAt)
}
