package woowacourse.shopping.data.cart

import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import java.time.LocalDateTime

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {
    override fun addCartProduct(cartProduct: CartProduct) {
        cartDao.insertCartProduct(cartProduct)
    }

    override fun getAll(): Cart {
        return cartDao.selectAll()
    }

    override fun getAllCount(): Int {
        return cartDao.selectAllCount()
    }

    override fun getPage(page: Int, sizePerPage: Int): Cart {
        return cartDao.selectPage(page, sizePerPage)
    }

    override fun deleteCartProductByTime(time: LocalDateTime) {
        cartDao.deleteCartProductByTime(time)
    }

    override fun getTotalAmount(): Int {
        return cartDao.getTotalAmount()
    }

    override fun getCartProductByProduct(product: Product): CartProduct? {
        return cartDao.selectCartProductByProduct(product)
    }

    override fun modifyCartProduct(cartProduct: CartProduct) {
        cartDao.updateCartProduct(cartProduct)
    }
}
