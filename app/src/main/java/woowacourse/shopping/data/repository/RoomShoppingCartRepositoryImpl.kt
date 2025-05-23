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
    override suspend fun findAll(): List<ShoppingCartItem> {
        return shoppingCartItemDao.findAll().map {
            it.toShoppingCartItem()
        }
    }

    override suspend fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem> {
        val item =
            shoppingCartItemDao.findAll().map {
                it.toShoppingCartItem()
            }
        return pageRequest.toPage(item, totalSize())
    }

    override suspend fun totalSize(): Int {
        return shoppingCartItemDao.count()
    }

    override suspend fun remove(item: ShoppingCartItem) {
        shoppingCartItemDao.delete(item.id)
    }

    override suspend fun save(item: ShoppingCartItem) {
        shoppingCartItemDao.saveOrInsert(
            item.toEntity(),
        )
    }

    override suspend fun update(item: ShoppingCartItem) {
        shoppingCartItemDao.update(
            item.toEntity(),
        )
    }

    override suspend fun totalQuantity(): Int {
        return shoppingCartItemDao.totalQuantity()
    }
}
