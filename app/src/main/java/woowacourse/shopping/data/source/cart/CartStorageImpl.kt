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
            val productTableId: Long? = cartDao.getProductTableId(product.id)
            if (productTableId == null) {
                cartDao.insert(
                    CartEntity(
                        productId = product.id,
                        count = count,
                    ),
                )
            } else {
                cartDao.updateCount(productId = product.id, count = count)
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
