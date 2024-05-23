package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.CartedProduct
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun fetchCartItems(page: Int): List<CartedProduct> {
        return cartDao.getCartedProducts(page, PAGE_SIZE)
    }

    override fun addCartItem(
        cartItem: CartItem
    ): Long {
        return cartDao.addCartItem(cartItem)
    }

    override fun fetchTotalCount(): Int {
        return cartDao.getTotalQuantity()
    }

    override fun updateQuantity(cartItemId: Long, quantity: Int) {
        cartDao.updateQuantity(cartItemId, quantity)
    }

    override fun removeCartItem(cartItem: CartItem) {
        thread {
            cartDao.deleteCartItem(cartItem)
        }
    }

    override fun removeAll() {
        cartDao.deleteAll()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
