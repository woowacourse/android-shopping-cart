package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.dao.CartProductDao
import woowacourse.shopping.data.local.entity.CartProduct

class RoomDataSource(private val cartProductDao: CartProductDao): LocalDataSource {
    override fun findProductByPaging(offset: Int, pageSize: Int): List<CartProduct> {
        return cartProductDao.findProductByPaging(offset, pageSize)
    }

    override fun findCartByPaging(offset: Int, pageSize: Int): List<CartProduct> {
        return cartProductDao.findCartByPaging(offset, pageSize)
    }
}