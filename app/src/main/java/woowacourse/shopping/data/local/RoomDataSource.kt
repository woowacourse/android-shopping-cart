package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.dao.CartProductDao
import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.data.local.entity.CartProductEntity

class RoomDataSource(private val cartProductDao: CartProductDao) : LocalDataSource {
    override fun findProductByPaging(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity> {
        return cartProductDao.findProductByPaging(offset, pageSize)
    }

    override fun findCartByPaging(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity> {
        return cartProductDao.findCartByPaging(offset, pageSize)
    }

    override fun findProductById(id: Long): CartProductEntity? {
        return cartProductDao.findProductById(id)
    }

    override fun saveCart(cartEntity: CartEntity): Long {
        cartProductDao.saveCart(cartEntity)
        return cartEntity.productId
    }

    override fun deleteCart(cartId: Long): Long {
        cartProductDao.deleteCart(cartId)
        return cartId
    }
}
