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
        callback: (List<CartProductEntity>) -> Unit,
    ) {
        thread {
            val list = cartProductDao.getCartProductsInRange(startIndex, endIndex)
            callback(list)
        }
    }

    override fun updateProduct(
        cartProductEntity: CartProductEntity,
        diff: Int,
        callback: (CartProductEntity?) -> Unit,
    ) {
        thread {
            val targetProduct = cartProductDao.getCartProduct(cartProductEntity.uid)
            if (targetProduct == null) {
                cartProductDao.insertCartProduct(cartProductEntity.copy(quantity = 1))
            } else if (targetProduct.quantity == 1 && diff == -1) {
                return@thread
            } else {
                cartProductDao.updateProduct(cartProductEntity.uid, diff)
            }

            val updatedProduct = cartProductDao.getCartProduct(cartProductEntity.uid)
            callback(updatedProduct)
        }
    }

    override fun getProductQuantity(
        id: Int,
        callback: (Int?) -> Unit,
    ) {
        thread {
            val quantity = cartProductDao.getProductQuantity(id)
            callback(quantity)
        }
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
