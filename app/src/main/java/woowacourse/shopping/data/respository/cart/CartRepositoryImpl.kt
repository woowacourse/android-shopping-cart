package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.CartProductModel

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override fun getCarts(startPosition: Int, cartItemCount: Int): List<CartProductModel> {
        return cartDao.getItems(startPosition, cartItemCount).map { it.toUIModel() }
    }

    override fun deleteCartByProductId(productId: Long) {
        cartDao.deleteAllProduct(productId)
    }

    override fun addCart(productId: Long, productCount: Int) {
        cartDao.insertProduct(productId, productCount)
    }
}
