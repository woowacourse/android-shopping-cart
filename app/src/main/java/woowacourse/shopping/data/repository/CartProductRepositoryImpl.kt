package woowacourse.shopping.data.repository

import androidx.annotation.IdRes
import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class CartProductRepositoryImpl(
    val cartProductDao: CartProductDao,
) : CartProductRepository {
    override fun insertCartProduct(cartProduct: CartProductEntity) {
        thread { cartProductDao.insertCartProduct(cartProduct) }
    }

    override fun deleteCartProduct(productId: Int) {
        thread { cartProductDao.deleteCartProduct(productId) }
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
    ) {
        thread {
            val targetProduct = cartProductDao.getCartProduct(productId)
            targetProduct?.let {
                if (targetProduct.quantity == 1 && diff == -1) {
                    return@thread
                } else {
                    cartProductDao.updateProduct(productId, diff)
                }
            }
        }
    }

    override fun getProduct(
        id: Int,
        callback: (CartProductEntity) -> Unit
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
