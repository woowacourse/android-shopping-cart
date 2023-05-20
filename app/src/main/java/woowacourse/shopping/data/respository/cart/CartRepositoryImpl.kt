package woowacourse.shopping.data.respository.cart

import android.content.Context
import woowacourse.shopping.data.database.CartDao
import woowacourse.shopping.data.model.CartEntity

class CartRepositoryImpl(context: Context) : CartRepository {
    private val cartDao = CartDao(context)
    override fun updateCartByProductId(productId: Long, count: Int, checked: Int) {
        cartDao.updateCartByProductId(productId, count, checked)
    }

    override fun updateCartCountByCartId(cartId: Long, count: Int) {
        cartDao.updateCartCountByCartId(cartId, count)
    }

    override fun updateCartCheckedByCartId(cartId: Long, checked: Boolean) {
        cartDao.updateCartCheckedByCartId(cartId, checked)
    }

    override fun getCarts(startPosition: Int): List<CartEntity> {
        return cartDao.getItemsFromStartPositionToDisplaySize(startPosition)
    }

    override fun getAllCarts(): List<CartEntity> {
        return cartDao.getAllItems()
    }

    override fun deleteAllCartByProductId(productId: Long) {
        cartDao.deleteAllProduct(productId)
    }

    override fun deleteCartByCartId(cartId: Long) {
        cartDao.deleteCart(cartId)
    }

    override fun deleteCartByProductId(productId: Long) {
        cartDao.deleteProduct(productId)
    }

    override fun addCart(productId: Long, count: Int) {
        cartDao.insertProduct(productId, count, 1)
    }
}
