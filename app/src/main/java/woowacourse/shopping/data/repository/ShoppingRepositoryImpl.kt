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
    override fun getAllGoods(): Set<ShoppingGoods> {
        var result: Set<ShoppingGoods> = emptySet()

        thread {
            result = shoppingDao.getAll().map { it.toShoppingGoods() }.toSet()
        }.join()

        return result
    }

    override fun insertGoods(
        id: Int,
        quantity: Int,
    ) {
        thread {
            shoppingDao.insert(ShoppingGoods(id, quantity).toShoppingEntity())
        }.join()
    }

    override fun increaseGoodsQuantity(
        id: Int,
        quantity: Int,
    ) {
        adjustGoodsQuantity(id, quantity)
    }

    override fun decreaseGoodsQuantity(
        id: Int,
        quantity: Int,
    ) {
        adjustGoodsQuantity(id, quantity)
    }

    override fun removeGoods(id: Int) {
        thread {
            shoppingDao.delete(id)
        }.join()
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<ShoppingGoods> {
        var result: List<ShoppingGoods> = emptyList()

        thread {
            result = shoppingDao.getPagedGoods(page, count).map { it.toShoppingGoods() }
        }.join()

        return result
    }

    override fun getGoodsById(id: Int): ShoppingGoods? {
        var result: ShoppingGoods? = null

        thread {
            result = shoppingDao.findGoodsById(id)?.toShoppingGoods()
        }.join()

        return result
    }

    private fun adjustGoodsQuantity(
        id: Int,
        quantity: Int,
    ) {
        thread {
            val itemInCart = shoppingDao.findGoodsById(id)

            if (itemInCart == null && quantity > 0) {
                shoppingDao.insert(ShoppingGoods(id, quantity).toShoppingEntity())
            } else if (itemInCart != null) {
                val updatedCount = itemInCart.quantity + quantity

                if (updatedCount <= 0) {
                    shoppingDao.delete(id)
                } else {
                    shoppingDao.updateQuantity(id, updatedCount)
                }
            }
        }.join()
    }
}
