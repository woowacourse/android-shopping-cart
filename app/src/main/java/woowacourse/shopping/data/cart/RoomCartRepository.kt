package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.dao.CartDao
import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.model.Quantity
import kotlin.IllegalArgumentException
import kotlin.concurrent.thread

class RoomCartRepository(private val cartDao: CartDao) : CartRepository {
    override fun increaseQuantity(productId: Long) {
        thread {
            val cartItem = cartDao.findOrNull(productId)
            if (cartItem == null) {
                cartDao.insert(CartItem(productId = productId, quantity = Quantity(1)))
                return@thread
            }
            var oldQuantity = cartItem.quantity
            cartDao.changeQuantity(productId, ++oldQuantity)
        }.join()
    }

    override fun decreaseQuantity(productId: Long) {
        thread {
            val cartItem = cartDao.findOrNull(productId)
            cartItem ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)

            var oldQuantity = cartItem.quantity
            if (oldQuantity.count == 1) {
                cartDao.delete(productId)
                return@thread
            }
            cartDao.changeQuantity(productId, --oldQuantity)
        }.join()
    }

    override fun changeQuantity(
        productId: Long,
        quantity: Quantity,
    ) {
        thread {
            val cartItem = cartDao.findOrNull(productId)
            if (cartItem == null) {
                cartDao.insert(CartItem(productId = productId, quantity = quantity))
                return@thread
            }
            cartDao.changeQuantity(productId, quantity)
        }.join()
    }

    override fun deleteCartItem(productId: Long) {
        thread {
            cartDao.findOrNull(productId) ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)
            cartDao.delete(productId)
        }.join()
    }

    override fun findOrNull(productId: Long): CartItem? {
        var cartItem: CartItem? = null
        thread {
            cartItem = cartDao.findOrNull(productId)
        }.join()
        return cartItem
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        thread {
            cartItems = cartDao.findRange(page, pageSize)
        }.join()
        return cartItems
    }

    override fun totalCartItemCount(): Int {
        var totalCartItemCount = 0
        thread {
            totalCartItemCount = cartDao.totalCount()
        }.join()
        return totalCartItemCount
    }

    companion object {
        private const val CANNOT_DELETE_MESSAGE = "삭제할 수 없습니다."
    }
}
