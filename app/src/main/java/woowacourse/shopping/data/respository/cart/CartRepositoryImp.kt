package woowacourse.shopping.data.respository.cart

import android.content.Context
import woowacourse.shopping.data.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.CartModel

class CartRepositoryImp(context: Context) : CartRepository {
    private val cartDao = CartDao(context)
    override fun getCarts(): List<CartModel> {
        return cartDao.getAll().map { it.toUIModel() }
    }

    override fun deleteCartByProductId(productId: Long) {
        cartDao.deleteAllProduct(productId)
    }

    override fun addCart(productId: Long) {
        cartDao.insertProduct(productId)
    }
}
