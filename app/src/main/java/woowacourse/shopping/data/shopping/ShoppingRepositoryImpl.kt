package woowacourse.shopping.data.shopping

import woowacourse.shopping.data.mapper.toShoppingEntity
import woowacourse.shopping.data.mapper.toShoppingGoods
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

    override fun addItemsWithCount(
        id: Int,
        count: Int,
    ) {
        updateItem(id, count)
    }

    override fun increaseItemCount(id: Int) {
        updateItem(id, 1)
    }

    override fun decreaseItemCount(id: Int) {
        updateItem(id, -1)
    }

    override fun removeItem(id: Int) {
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

    private fun updateItem(
        goodsId: Int,
        quantity: Int,
    ) {
        thread {
            val itemInCart = shoppingDao.findGoodsById(goodsId)

            if (itemInCart == null && quantity > 0) {
                shoppingDao.insert(ShoppingGoods(goodsId, quantity).toShoppingEntity())
            } else if (itemInCart != null) {
                val updatedCount = itemInCart.quantity + quantity

                if (updatedCount <= 0) {
                    shoppingDao.delete(goodsId)
                } else {
                    shoppingDao.updateQuantity(goodsId, updatedCount)
                }
            }
        }.join()
    }
}
