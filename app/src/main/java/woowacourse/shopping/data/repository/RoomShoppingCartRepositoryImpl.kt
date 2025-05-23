package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.ShoppingCartItemDao
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.mapper.toEntity
import woowacourse.shopping.mapper.toShoppingCartItem

class RoomShoppingCartRepositoryImpl(
    private val shoppingCartItemDao: ShoppingCartItemDao,
) : ShoppingCartRepository {
    override fun findAll(): List<ShoppingCartItem> {
        return shoppingCartItemDao.findAll().map {
            it.toShoppingCartItem()
        }
    }

    override fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem> {
        val item =
            shoppingCartItemDao.findAll().map {
                it.toShoppingCartItem()
            }
        return pageRequest.toPage(item, totalSize())
    }

    override fun totalSize(): Int {
        return shoppingCartItemDao.count()
    }

    override fun remove(item: ShoppingCartItem) {
        shoppingCartItemDao.delete(item.id)
    }

    override fun save(item: ShoppingCartItem) {
        shoppingCartItemDao.saveOrInsert(
            item.toEntity(),
        )
    }

    override fun update(item: ShoppingCartItem) {
        shoppingCartItemDao.update(
            item.toEntity(),
        )
    }

    override fun totalQuantity(): Int {
        return shoppingCartItemDao.totalQuantity()
    }
}
