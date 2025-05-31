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
    override fun getAllGoods(
        onSuccess: (Set<ShoppingGoods>) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                onSuccess(shoppingDao.getAll().map { it.toShoppingGoods() }.toSet())
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun insertGoods(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                shoppingDao.insert(ShoppingGoods(id, quantity).toShoppingEntity())
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun increaseGoodsQuantity(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                shoppingDao.increaseOrInsert(id, quantity)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun decreaseGoodsQuantity(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                shoppingDao.decreaseOrDelete(id, quantity)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun removeGoods(
        id: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                shoppingDao.delete(id)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<ShoppingGoods>) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                onSuccess(shoppingDao.getPagedGoods(page, count).map { it.toShoppingGoods() })
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }
}
