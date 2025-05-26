package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.ShoppingDao
import woowacourse.shopping.data.entity.toShoppingEntity
import woowacourse.shopping.data.entity.toShoppingGoods
import woowacourse.shopping.domain.model.ShoppingGoods
import woowacourse.shopping.domain.repository.ShoppingRepository
import kotlin.concurrent.thread

class ShoppingRepositoryImpl(
    private val shoppingDao: ShoppingDao,
) : ShoppingRepository {
    override fun getAllGoods(onSuccess: (Set<ShoppingGoods>) -> Unit) {
        thread {
            onSuccess(shoppingDao.getAll().map { it.toShoppingGoods() }.toSet())
        }
    }

    override fun insertGoods(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
    ) {
        thread {
            shoppingDao.insert(ShoppingGoods(id, quantity).toShoppingEntity())
            onSuccess()
        }
    }

    override fun increaseGoodsQuantity(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
    ) {
        thread {
            shoppingDao.increaseOrInsert(id, quantity)
            onSuccess()
        }
    }

    override fun decreaseGoodsQuantity(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
    ) {
        thread {
            shoppingDao.decreaseOrDelete(id, quantity)
            onSuccess()
        }
    }

    override fun removeGoods(
        id: Int,
        onSuccess: () -> Unit,
    ) {
        thread {
            shoppingDao.delete(id)
            onSuccess()
        }
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<ShoppingGoods>) -> Unit,
    ) {
        thread {
            onSuccess(shoppingDao.getPagedGoods(page, count).map { it.toShoppingGoods() })
        }
    }
}
