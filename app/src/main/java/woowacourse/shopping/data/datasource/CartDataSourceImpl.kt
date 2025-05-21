package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity

class CartDataSourceImpl(
    private val cartDao: CartDao,
) : CartDataSource {
    override fun addCartItem(productId: Long): Result<Unit> =
        runCatching {
            val entity = CartEntity(productId)
            cartDao.insertOrUpdate(entity)
        }

    override fun decreaseCartItemQuantity(productId: Long): Result<Unit> =
        runCatching {
            cartDao.decreaseOrDelete(productId)
        }

    override fun deleteCartItem(productId: Long): Result<Unit> =
        runCatching {
            cartDao.delete(productId)
        }

    override fun loadCartItems(
        offset: Int,
        limit: Int,
    ): Result<List<CartEntity>> =
        runCatching {
            cartDao.getCartItemPaged(limit, offset)
        }

    override fun findQuantityByProductId(productId: Long): Result<Int> =
        runCatching {
            cartDao.findQuantityByProductId(productId)
        }

    override fun existsItemCreatedAfter(createdAt: Long): Result<Boolean> =
        runCatching {
            cartDao.existsItemCreatedAfter(createdAt)
        }
}
