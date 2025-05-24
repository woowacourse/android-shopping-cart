package woowacourse.shopping.data.source.cart

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.TableCartItem
import kotlin.concurrent.thread

class CartStorageImpl private constructor(
    private val cartDao: CartDao,
) : CartStorage {
    override fun getAllProductsSize(onResult: (Int) -> Unit) {
        thread {
            onResult(cartDao.getAllProductsSize())
        }
    }

    override fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<TableCartItem>) -> Unit,
    ) {
        thread {
            val cartItem =
                cartDao
                    .getLimitProducts(limit, offset)
                    .map { TableCartItem(it.productId, it.count) }
            onResult(cartItem)
        }
    }

    override fun addProduct(
        product: Product,
        count: Int,
    ) {
        thread {
            val cartItem = cartDao.getCartItem(product.id)
            if (cartItem == null) {
                cartDao.insert(
                    CartEntity(
                        productId = product.id,
                        count = count,
                    ),
                )
            } else {
                val newCount = count + cartItem.count
                cartDao.updateCount(productId = product.id, count = newCount)
            }
        }
    }

    override fun deleteProduct(cartItemId: Long) {
        thread {
            cartDao.deleteById(cartItemId)
        }
    }

    companion object {
        private var instance: CartStorageImpl? = null

        fun initialize(cartDao: CartDao): CartStorageImpl = instance ?: CartStorageImpl(cartDao).also { instance = it }
    }
}
