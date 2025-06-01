package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import kotlin.concurrent.thread

class CartProductRepositoryImpl(
    val cartProductDao: CartProductDao,
) : CartProductRepository {
    override fun insertCartProduct(
        cartProduct: CartProductEntity,
        callback: (Unit) -> Unit,
    ) {
        thread {
            cartProductDao.insertCartProduct(cartProduct)
            callback(Unit)
        }
    }

    override fun deleteCartProduct(
        productId: Int,
        callback: (Unit) -> Unit,
    ) {
        thread {
            cartProductDao.deleteCartProduct(productId)
            callback(Unit)
        }
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

    override fun updateProductQuantity(
        productId: Int,
        diff: Int,
        callback: (Unit) -> Unit,
    ) {
        thread {
            val targetProduct = cartProductDao.getCartProduct(productId)
            targetProduct?.let {
                if (targetProduct.quantity == 1 && diff == -1) {
                    return@thread
                } else {
                    cartProductDao.updateProduct(productId, diff)
                }
                callback(Unit)
            }
        }
    }

    override fun getProduct(
        id: Int,
        callback: (CartProductEntity) -> Unit,
    ) {
        thread {
            val product = cartProductDao.getCartProduct(id)
            product?.let { callback(it) }
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
