package woowacourse.shopping.data.datasource.impl

import woowacourse.shopping.data.dao.ShoppingCartItemDao
import woowacourse.shopping.data.datasource.CartItemDataSource
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import kotlin.concurrent.thread

class RoomCartItemDataSource(private val cartItemDao: ShoppingCartItemDao) : CartItemDataSource {
    override fun insert(shoppingCartItem: ShoppingCartItemEntity) {
        thread {
            cartItemDao.insert(shoppingCartItem)
        }.join()
    }

    override fun insertAll(shoppingCartItems: List<ShoppingCartItemEntity>) {
        thread {
            cartItemDao.insertAll(shoppingCartItems)
        }.join()
    }

    override fun increaseCount(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            cartItemDao.increaseCount(productId, quantity)
        }.join()
    }

    override fun decreaseCount(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            cartItemDao.decreaseCount(productId, quantity)
        }.join()
    }

    override fun delete(productId: Long) {
        thread {
            cartItemDao.delete(productId)
        }.join()
    }

    override fun shoppingCartItems(): List<ShoppingCartItemEntity> {
        var items = emptyList<ShoppingCartItemEntity>()
        thread {
            items = cartItemDao.shoppingCartItems()
        }.join()
        return items
    }

    override fun shoppingCartItems(
        pageSize: Int,
        pageIndex: Int,
    ): List<ShoppingCartItemEntity> {
        var items = emptyList<ShoppingCartItemEntity>()
        thread {
            items = cartItemDao.shoppingCartItems(pageSize, pageIndex)
        }.join()
        return items
    }

    override fun itemCount(): Int {
        var count = 0
        thread {
            count = cartItemDao.itemCount()
        }.join()
        return count
    }

    override fun cartItemById(productId: Long): CartItem {
        var result: CartItem = CartItem.Fail
        thread {
            val cartItem = cartItemDao.cartItemById(productId)
            if (cartItem != null) {
                result = CartItem.Success(cartItem)
            }
        }.join()
        return result
    }

    override fun updateTotalQuantity(
        productId: Long,
        newQuantity: Int,
    ) {
        thread {
            cartItemDao.updateTotalQuantity(productId, newQuantity)
        }.join()
    }
}

sealed interface CartItem {
    data class Success(val value: ShoppingCartItemEntity) : CartItem

    data object Fail : CartItem
}
