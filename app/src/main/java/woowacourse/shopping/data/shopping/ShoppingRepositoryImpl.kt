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

    override fun increaseItemQuantity(
        id: Int,
        quantity: Int,
    ) {
        adjustItemQuantity(id, quantity)
    }

    override fun decreaseItemQuantity(
        id: Int,
        quantity: Int,
    ) {
        adjustItemQuantity(id, quantity)
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

    private fun adjustItemQuantity(
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
