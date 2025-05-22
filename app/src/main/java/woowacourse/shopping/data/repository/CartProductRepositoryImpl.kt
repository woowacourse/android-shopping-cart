package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import kotlin.concurrent.thread

class CartProductRepositoryImpl(
    val cartProductDao: CartProductDao,
) : CartProductRepository {
    override fun insertCartProduct(cartProduct: CartProductEntity) {
        thread { cartProductDao.insertCartProduct(cartProduct) }
    }

    override fun deleteCartProduct(cartProduct: CartProductEntity) {
        thread { cartProductDao.deleteCartProduct(cartProduct) }
    }

    override fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<CartProductEntity> = cartProductDao.getCartProductsInRange(startIndex, endIndex)

    override fun updateProduct(product: CartProductEntity) {
        thread { cartProductDao.updateProduct(product) }
    }

    override fun getAllProductsSize(callback: (Int) -> Unit) {
        thread {
            val size = cartProductDao.getAllProductsSize()
            callback(size)
        }
    }

    override fun getCartItemSize(callback: (Int) -> Unit) {
        thread {
            val size = cartProductDao.getCartItemSize()
            callback(size)
        }
    }
}
