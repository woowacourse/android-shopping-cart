package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity

class CartDataSourceImpl(
    private val cartDao: CartDao,
) : CartDataSource {
    override fun getAll(): List<CartEntity> = cartDao.getAll()

    override fun getTotalQuantity(): Int = cartDao.getTotalQuantity()

    override fun loadCartItems(
        offset: Int,
        limit: Int,
    ): List<CartEntity> = cartDao.getCartItemPaged(limit, offset)

    override fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
    ) {
        val entity = CartEntity(productId, increaseQuantity)
        cartDao.insertOrUpdate(entity, increaseQuantity)
    }

    override fun existsItemCreatedAfter(createdAt: Long): Boolean = cartDao.existsItemCreatedAfter(createdAt)

    override fun findCartItemByProductId(productId: Long): CartEntity =
        cartDao.findByProductId(productId)
            ?: throw NoSuchElementException(NO_SUCH_ELEMENT_MESSAGE.format(productId))

    override fun decreaseCartItemQuantity(productId: Long) {
        cartDao.decreaseOrDelete(productId)
    }

    override fun deleteCartItem(productId: Long) {
        cartDao.delete(productId)
    }

    companion object {
        private const val NO_SUCH_ELEMENT_MESSAGE = "%d에 해당하는 상품을 찾을 수 없습니다."
    }
}
