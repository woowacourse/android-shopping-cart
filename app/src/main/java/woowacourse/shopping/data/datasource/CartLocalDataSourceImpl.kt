package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.entity.CartEntity

class CartLocalDataSourceImpl(private val cartDao: CartDao) : CartLocalDataSource {
    override fun insert(item: CartEntity) {
        cartDao.insert(item)
    }

    override fun getAll() = cartDao.getAll()

    override fun getByIds(ids: List<Long>): List<CartEntity?> = ids.map { cartDao.getById(it) }

    override fun totalSize() = cartDao.totalSize()

    override fun deleteById(id: Long) {
        cartDao.deleteById(id)
    }

    override fun getPaged(
        offset: Int,
        limit: Int,
    ): List<CartEntity> = cartDao.getPaged(offset, limit)

    override fun hasOnlyPage(limit: Int): Boolean {
        val result = cartDao.totalSize() <= limit
        return result
    }
}
