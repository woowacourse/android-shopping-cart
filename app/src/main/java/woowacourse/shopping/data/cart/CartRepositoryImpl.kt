package woowacourse.shopping.data.cart

import com.example.domain.CartProduct
import com.example.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {

    override fun getAll(): List<CartProduct> {
        return cartDao.getAll()
    }

    override fun getCartProduct(productId: Int): CartProduct? {
        return cartDao.getCartProduct(productId)
    }

    override fun addProduct(productId: Int, count: Int) {
        cartDao.addColumn(productId, count)
    }

    override fun deleteCartProduct(productId: Int) {
        cartDao.deleteColumn(productId)
    }

    override fun updateCartProductCount(productId: Int, count: Int) {
        cartDao.updateCartProductCount(productId, count)
    }

    override fun updateCartProductChecked(productId: Int, checked: Boolean) {
        cartDao.updateCartProductChecked(productId, checked)
    }
}
